package pl.edu.agh.gg.productions;

import org.jgrapht.Graph;
import pl.edu.agh.gg.domain.HaxEdge;
import pl.edu.agh.gg.domain.Rgb;
import pl.edu.agh.gg.domain.VertexLike;

import java.awt.*;
import java.awt.image.BufferedImage;

abstract public class Production {
    private BufferedImage image;
    private Graph<VertexLike, HaxEdge> graph;

    public Production(BufferedImage image, Graph<VertexLike, HaxEdge> graph) {
        this.image = image;
        this.graph = graph;
    }

    public BufferedImage getImage() {
        return image;
    }

    public Rgb getRgb(int x, int y) {
        Color color = new Color(image.getRGB(x, y));
        return new Rgb(color.getRed(), color.getGreen(), color.getBlue());
    }

    public Graph<VertexLike, HaxEdge> getGraph() {
        return graph;
    }


    /**
     * @param center żeby dało się ogarnąć gdzie przyłożyć produkcję
     */
    public abstract void apply(VertexLike center) throws CannotApplyProductionException;
}
