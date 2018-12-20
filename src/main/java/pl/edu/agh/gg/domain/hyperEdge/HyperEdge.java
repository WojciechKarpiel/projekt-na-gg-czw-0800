package pl.edu.agh.gg.domain.hyperEdge;

import pl.edu.agh.gg.domain.Vertex;
import pl.edu.agh.gg.domain.VertexLike;

import java.util.List;

abstract public class HyperEdge extends VertexLike {
    private EdgeLabel edgeLabel;
    private List<Vertex> connectedVertices;

    public HyperEdge(EdgeLabel edgeLabel, List<Vertex> connectedVertices) {
        super(EntityType.EDGE);
        this.edgeLabel = edgeLabel;
        this.connectedVertices = connectedVertices;
    }

    public EdgeLabel getEdgeLabel() {
        return edgeLabel;
    }

    public void setEdgeLabel(EdgeLabel edgeLabel) {
        this.edgeLabel = edgeLabel;
    }

    public List<Vertex> getConnectedVertices() {
        return connectedVertices;
    }

    public void setConnectedVertices(List<Vertex> connectedVertices) {
        this.connectedVertices = connectedVertices;
    }

    public enum EdgeLabel {
        S, B, I, F;
    }

    // Nie można robić takiego `equals` bo wtedy doda sie tylko jedna krawędź danego typu :(
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        HyperEdge hyperEdge = (HyperEdge) o;
//        return edgeLabel == hyperEdge.edgeLabel;
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(edgeLabel);
//    }

    @Override
    public String toString() {
        return "HyperEdge{" +
                "edgeLabel=" + edgeLabel +
                ", id=" + getId() +
                '}';
    }
}
