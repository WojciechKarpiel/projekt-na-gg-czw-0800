package pl.edu.agh.gg.drawing;

import pl.edu.agh.gg.HyperGraph;
import pl.edu.agh.gg.approximation.Approximation;
import pl.edu.agh.gg.domain.Geom;
import pl.edu.agh.gg.domain.Rgb;
import pl.edu.agh.gg.domain.Vertex;
import pl.edu.agh.gg.domain.hyperEdge.HyperEdgeB;
import pl.edu.agh.gg.domain.hyperEdge.HyperEdgeI;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

public class Task18 {

    public static Approximation approx(HyperGraph hyperGraph) {
        Set<HyperEdgeI> iEdges = hyperGraph.vertexSet().stream()
                .filter(v -> v instanceof HyperEdgeI)
                .map(v -> (HyperEdgeI) v)
                .collect(toSet());

        int maxX = getMaxX(iEdges);
        int maxY = getMaxY(iEdges);

        double[][] approxR = new double[maxX][maxY];
        double[][] approxG = new double[maxX][maxY];
        double[][] approxB = new double[maxX][maxY];

        for (HyperEdgeI e : iEdges) {

            Vertex[] vertices = sortVertices(e.getConnectedVertices());
            Vertex v1 = vertices[1];
            Vertex v2 = vertices[3];
            Vertex v3 = vertices[0];
            Vertex v4 = vertices[2];

            int x1 = v3.getGeom().getX();
            int y1 = v3.getGeom().getY();
            int x2 = v2.getGeom().getX();
            int y2 = v2.getGeom().getY();

            int r1 = v1.getRgb().getR(), g1 = v1.getRgb().getG(), b1 = v1.getRgb().getB();
            int r2 = v2.getRgb().getR(), g2 = v2.getRgb().getG(), b2 = v2.getRgb().getB();
            int r3 = v3.getRgb().getR(), g3 = v3.getRgb().getG(), b3 = v3.getRgb().getB();
            int r4 = v4.getRgb().getR(), g4 = v4.getRgb().getG(), b4 = v4.getRgb().getB();

            for (int px = x1; px < x2; px++) {
                for (int py = y1; py < y2; py++) {
                    approxR[px][py] += r1 * (1.0 - (1.0 * (px - x1) / (x2 - x1))) * (1.0 * (py - y1) / (y2 - y1));
                    approxG[px][py] += g1 * (1.0 - (1.0 * (px - x1) / (x2 - x1))) * (1.0 * (py - y1) / (y2 - y1));
                    approxB[px][py] += b1 * (1.0 - (1.0 * (px - x1) / (x2 - x1))) * (1.0 * (py - y1) / (y2 - y1));

                    approxR[px][py] += r2 * ((1.0 * (px - x1) / (x2 - x1))) * (1.0 * (py - y1) / (y2 - y1));
                    approxG[px][py] += g2 * ((1.0 * (px - x1) / (x2 - x1))) * (1.0 * (py - y1) / (y2 - y1));
                    approxB[px][py] += b2 * ((1.0 * (px - x1) / (x2 - x1))) * (1.0 * (py - y1) / (y2 - y1));

                    approxR[px][py] += r3 * (1.0 - (1.0 * (px - x1) / (x2 - x1))) * (1.0 - (1.0 * (py - y1) / (y2 - y1)));
                    approxG[px][py] += g3 * (1.0 - (1.0 * (px - x1) / (x2 - x1))) * (1.0 - (1.0 * (py - y1) / (y2 - y1)));
                    approxB[px][py] += b3 * (1.0 - (1.0 * (px - x1) / (x2 - x1))) * (1.0 - (1.0 * (py - y1) / (y2 - y1)));

                    approxR[px][py] += r4 * (1.0 * (px - x1) / (x2 - x1)) * (1.0 - (1.0 * (py - y1) / (y2 - y1)));
                    approxG[px][py] += g4 * (1.0 * (px - x1) / (x2 - x1)) * (1.0 - (1.0 * (py - y1) / (y2 - y1)));
                    approxB[px][py] += b4 * (1.0 * (px - x1) / (x2 - x1)) * (1.0 - (1.0 * (py - y1) / (y2 - y1)));
                }
            }
        }

        return new Approximation(approxR, approxG, approxB);
    }

    public static void main(String[] args) {

        HyperGraph graph = new HyperGraph();

        Vertex v1 = new Vertex(new Geom(0, 80), new Rgb(234, 226, 183), Vertex.Label.V);
        Vertex v2 = new Vertex(new Geom(50, 80), new Rgb(252, 191, 73), Vertex.Label.V);
        Vertex v3 = new Vertex(new Geom(100, 80), new Rgb(214, 40, 40), Vertex.Label.V);
        Vertex v4 = new Vertex(new Geom(0, 40), new Rgb(247, 127, 0), Vertex.Label.V);
        Vertex v5 = new Vertex(new Geom(50, 40), new Rgb(0, 48, 73), Vertex.Label.V);
        Vertex v6 = new Vertex(new Geom(100, 40), new Rgb(214, 40, 40), Vertex.Label.V);
        Vertex v7 = new Vertex(new Geom(0, 0), new Rgb(252, 191, 73), Vertex.Label.V);
        Vertex v8 = new Vertex(new Geom(50, 0), new Rgb(214, 40, 40), Vertex.Label.V);
        Vertex v9 = new Vertex(new Geom(100, 0), new Rgb(0, 48, 73), Vertex.Label.V);

//        Vertex v1 = new Vertex(new Geom(0, 80), new Rgb(100, 0, 0), Vertex.Label.V);
//        Vertex v2 = new Vertex(new Geom(50, 80), new Rgb(255, 100, 0), Vertex.Label.V);
//        Vertex v3 = new Vertex(new Geom(100, 80), new Rgb(255, 255, 255), Vertex.Label.V);
//        Vertex v4 = new Vertex(new Geom(0, 40), new Rgb(0, 100, 0), Vertex.Label.V);
//        Vertex v5 = new Vertex(new Geom(50, 40), new Rgb(0, 0, 0), Vertex.Label.V);
//        Vertex v6 = new Vertex(new Geom(100, 40), new Rgb(0, 100, 0), Vertex.Label.V);
//        Vertex v7 = new Vertex(new Geom(0, 0), new Rgb(255, 0, 255), Vertex.Label.V);
//        Vertex v8 = new Vertex(new Geom(50, 0), new Rgb(255, 255, 255), Vertex.Label.V);
//        Vertex v9 = new Vertex(new Geom(100, 0), new Rgb(0, 255, 0), Vertex.Label.V);

        HyperEdgeB b1 = new HyperEdgeB(Arrays.asList(v1, v2));
        HyperEdgeB b2 = new HyperEdgeB(Arrays.asList(v1, v4));
        HyperEdgeI i1 = new HyperEdgeI(Arrays.asList(v1, v2, v4, v5), false);
        HyperEdgeI i2 = new HyperEdgeI(Arrays.asList(v2, v3, v5, v6), false);
        HyperEdgeI i3 = new HyperEdgeI(Arrays.asList(v4, v5, v7, v8), false);
        HyperEdgeI i4 = new HyperEdgeI(Arrays.asList(v5, v6, v8, v9), false);
        graph.add(v1);
        graph.add(v2);
        graph.add(v3);
        graph.add(v4);
        graph.add(v5);
        graph.add(v6);
        graph.add(v7);
        graph.add(v8);
        graph.add(v9);
        graph.add(b1);
        graph.add(b2);
        graph.add(i1);
        graph.add(i2);
        graph.add(i3);
        graph.add(i4);

        Approximation approx = approx(graph);
        Drawer.createPicture(approx, "obrazek.bmp");
    }

    private static int getMaxX(Set<HyperEdgeI> edges) {
        return edges.stream().map(e -> getMaxX(e.getConnectedVertices())).max(Integer::compareTo).get();
    }

    private static int getMaxY(Set<HyperEdgeI> edges) {
        return edges.stream().map(e -> getMaxY(e.getConnectedVertices())).max(Integer::compareTo).get();
    }

    private static int getMaxX(List<Vertex> vertices) {
        return vertices.stream().map(v -> v.getGeom().getX()).max(Integer::compareTo).get();
    }

    private static int getMaxY(List<Vertex> vertices) {
        return vertices.stream().map(v -> v.getGeom().getY()).max(Integer::compareTo).get();
    }

    /**
     * 1 --- 3
     * | \ / |
     * |  I  |
     * | / \ |
     * 0 --- 2
     */
    private static Vertex[] sortVertices(List<Vertex> vertices) {
        Comparator<Vertex> comparatorY = Comparator.comparing(v -> v.getGeom().getY());
        Comparator<Vertex> comparatorX = Comparator.comparing(v -> v.getGeom().getX());
        Comparator<Vertex> comparator = comparatorX.thenComparing(comparatorY);

        vertices.sort(comparator);
        Vertex[] verticesArray = new Vertex[vertices.size()];
        vertices.toArray(verticesArray);

        return verticesArray;
    }
}
