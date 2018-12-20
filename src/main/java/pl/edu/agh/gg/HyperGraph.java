package pl.edu.agh.gg;

import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import pl.edu.agh.gg.domain.VertexLike;

public class HyperGraph extends SimpleGraph<VertexLike, DefaultEdge> {
    public HyperGraph() {
        super(DefaultEdge.class);
    }

    public void add(VertexLike vertexLike) {
        vertexLike.<Void>map(
                v -> {
                    addVertex(v);
                    return null;
                },
                e -> {
                    addVertex(e);
                    e.getConnectedVertices().forEach(v -> addVertex(v));
                    e.getConnectedVertices().forEach(v -> addEdge(e, v));
                    return null;
                }
        );
    }
}
