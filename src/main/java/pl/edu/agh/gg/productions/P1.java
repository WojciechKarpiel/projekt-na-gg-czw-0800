package pl.edu.agh.gg.productions;

import pl.edu.agh.gg.HyperGraph;
import pl.edu.agh.gg.domain.Geom;
import pl.edu.agh.gg.domain.Vertex;
import pl.edu.agh.gg.domain.VertexLike;
import pl.edu.agh.gg.domain.hyperEdge.HyperEdge;
import pl.edu.agh.gg.domain.hyperEdge.HyperEdgeB;
import pl.edu.agh.gg.domain.hyperEdge.HyperEdgeI;

import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Optional;


public class P1 extends Production {
    public P1(BufferedImage image, HyperGraph graph) {
        super(image, graph);
    }

    @Override
    public void apply(VertexLike center) throws CannotApplyProductionException {
        HyperGraph graph = getGraph();
        Optional<HyperEdge> sOpt = center.getAsEdge().filter(e -> e.getEdgeLabel() == HyperEdge.EdgeLabel.S &&
                graph.vertexSet().contains(e));
        if (!sOpt.isPresent()) throw new CannotApplyProductionException();
        HyperEdge s = sOpt.get();
        int maxX = getImage().getWidth() - 1;
        int maxY = getImage().getHeight() - 1;

        graph.removeVertex(s);
        Vertex v1 = new Vertex(new Geom(0, 0), getRgb(0, 0), Vertex.Label.V);
        Vertex v2 = new Vertex(new Geom(0, maxY), getRgb(0, maxY), Vertex.Label.V);
        Vertex v3 = new Vertex(new Geom(maxX, maxY), getRgb(maxX, maxY), Vertex.Label.V);
        Vertex v4 = new Vertex(new Geom(maxX, 0), getRgb(maxX, 0), Vertex.Label.V);
        HyperEdge b1 = new HyperEdgeB(Arrays.asList(v1, v2));
        HyperEdge b2 = new HyperEdgeB(Arrays.asList(v2, v3));
        HyperEdge b3 = new HyperEdgeB(Arrays.asList(v3, v4));
        HyperEdge b4 = new HyperEdgeB(Arrays.asList(v4, v1));

        HyperEdge i = new HyperEdgeI(Arrays.asList(v1, v2, v3, v4), false);

        graph.add(i);
        graph.add(b1);
        graph.add(b2);
        graph.add(b3);
        graph.add(b4);

    }
}
