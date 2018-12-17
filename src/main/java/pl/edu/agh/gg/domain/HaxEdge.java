package pl.edu.agh.gg.domain;

import org.jgrapht.graph.DefaultEdge;

/**
 * Tylko po to żeby upublicznić tych ziomków
 */
public class HaxEdge extends DefaultEdge {

    public VertexLike getSource() {
        return (VertexLike) super.getSource();
    }

    public VertexLike getTarget() {
        return (VertexLike) super.getTarget();
    }
}
