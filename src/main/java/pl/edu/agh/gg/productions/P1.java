package pl.edu.agh.gg.productions;

import org.jgrapht.Graph;
import pl.edu.agh.gg.domain.*;

import java.awt.image.BufferedImage;
import java.util.Optional;


public class P1 extends Production {
    public P1(BufferedImage image, Graph<VertexLike, HaxEdge> graph) {
        super(image, graph);
    }

    @Override
    public void apply(VertexLike center) throws CannotApplyProductionException {
        Graph<VertexLike, HaxEdge> graph = getGraph();
        Optional<HyperEdge> sOpt = center.getAsEdge().filter(e -> e.getEdgeType() == HyperEdge.EdgeType.S &&
                graph.vertexSet().contains(e));
        if (!sOpt.isPresent()) throw new CannotApplyProductionException();
        HyperEdge s = sOpt.get();
        int maxX = getImage().getWidth() - 1;
        int maxY = getImage().getHeight() - 1;

        graph.removeVertex(s);
        Vertex v1 = new Vertex(new Position(0, 0), getRgb(0, 0));
        Vertex v2 = new Vertex(new Position(0, maxY), getRgb(0, maxY));
        Vertex v3 = new Vertex(new Position(maxX, maxY), getRgb(maxX, maxY));
        Vertex v4 = new Vertex(new Position(maxX, 0), getRgb(maxX, 0));
        HyperEdge b1 = new HyperEdge(HyperEdge.EdgeType.B);
        HyperEdge b2 = new HyperEdge(HyperEdge.EdgeType.B);
        HyperEdge b3 = new HyperEdge(HyperEdge.EdgeType.B);
        HyperEdge b4 = new HyperEdge(HyperEdge.EdgeType.B);
        HyperEdge i = new HyperEdge(HyperEdge.EdgeType.I_NOBREAK);
        graph.addVertex(v1);
        graph.addVertex(v2);
        graph.addVertex(v3);
        graph.addVertex(v4);
        graph.addVertex(i);
        graph.addVertex(b1);
        graph.addVertex(b2);
        graph.addVertex(b3);
        graph.addVertex(b4);

        // jedyne prawile "krawędzie" to Vertex-HyperEdge i HyperEdge-Vertex
        graph.addEdge(v1, i);
        graph.addEdge(v2, i);
        graph.addEdge(v3, i);
        graph.addEdge(v4, i);

        graph.addEdge(v1, b1);
        graph.addEdge(v2, b1);
        graph.addEdge(v2, b2);
        graph.addEdge(v3, b2);
        graph.addEdge(v3, b3);
        graph.addEdge(v4, b3);
        graph.addEdge(v4, b4);
        graph.addEdge(v1, b4);

    }
}
