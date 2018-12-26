package pl.edu.agh.gg.approximation;

import pl.edu.agh.gg.domain.Rgb;
import pl.edu.agh.gg.domain.Vertex;
import pl.edu.agh.gg.domain.hyperEdge.HyperEdgeI;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class ElementApproxError {
    private BufferedImage image;

    public ElementApproxError(BufferedImage image) {
        this.image = image;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public double calculateError(HyperEdgeI edgeI) throws CannotCalculateErrorException {
        // making copy of vertices list, because original list can be immutable
        List<Vertex> vertices = new ArrayList<>(edgeI.getConnectedVertices());
        if (vertices.size() != 4) {
            throw new CannotCalculateErrorException();
        }

        vertices.sort((v1, v2) -> {
            int vDelta = v2.getGeom().getY() - v1.getGeom().getY();
            if (vDelta == 0) {
                return v1.getGeom().getX() - v2.getGeom().getX();
            }
            return vDelta;
        });
        Vertex v1 = vertices.get(0);
        Vertex v2 = vertices.get(1);
        Vertex v3 = vertices.get(2);
        Vertex v4 = vertices.get(3);

        int xLength = v2.getGeom().getX() - v1.getGeom().getX();
        int yLength = v1.getGeom().getY() - v3.getGeom().getY();

        double error = 0.0;
        for (int x = v1.getGeom().getX(); x <= v2.getGeom().getX(); x++) {
            for (int y = v3.getGeom().getY(); y <= v1.getGeom().getY(); y++) {
                Rgb color = getRgb(x, y);

                // diff array is not needed because everything can be calculated "in place" per pixel
                double diff_r = 0.0;
                double diff_g = 0.0;
                double diff_b = 0.0;
                diff_r = color.getR();
                diff_g = color.getG();
                diff_b = color.getB();

                double fx = (x - v1.getGeom().getX()) / xLength;
                double nfx = 1 - fx;
                double fy = (y - v3.getGeom().getY()) / yLength;
                double nfy = 1 - fy;

                diff_r -= v1.getRgb().getR() * nfx * fy;
                diff_g -= v1.getRgb().getG() * nfx * fy;
                diff_b -= v1.getRgb().getB() * nfx * fy;

                diff_r -= v2.getRgb().getR() * fx * fy;
                diff_g -= v2.getRgb().getG() * fx * fy;
                diff_b -= v2.getRgb().getB() * fx * fy;

                diff_r -= v3.getRgb().getR() * nfx * nfy;
                diff_g -= v3.getRgb().getG() * nfx * nfy;
                diff_b -= v3.getRgb().getB() * nfx * nfy;

                diff_r -= v4.getRgb().getR() * fx * nfy;
                diff_g -= v4.getRgb().getG() * fx * nfy;
                diff_b -= v4.getRgb().getB() * fx * nfy;

                error += 0.5 * Math.pow(diff_r, 2) +
                        0.3 * Math.pow(diff_g, 2) +
                        0.2 * Math.pow(diff_b, 2);
            }
        }
        return error;
    }

    public Rgb getRgb(int x, int y) {
        Color color = new Color(image.getRGB(x, y));
        return new Rgb(color.getRed(), color.getGreen(), color.getBlue());
    }
}
