package pl.edu.agh.gg.domain.hyperEdge;

import pl.edu.agh.gg.domain.Geom;
import pl.edu.agh.gg.domain.Vertex;
import pl.edu.agh.gg.domain.VertexLike;

import java.util.List;

abstract public class HyperEdge extends VertexLike {
    private EdgeLabel edgeLabel;
    private List<Vertex> connectedVertices;
    private Geom geom;

    public HyperEdge(EdgeLabel edgeLabel, List<Vertex> connectedVertices) {
        super(EntityType.EDGE);
        this.edgeLabel = edgeLabel;
        setConnectedVertices(connectedVertices);
    }

    public Geom getGeom() {
        return geom;
    }

    public void setGeom(Geom geom) {
        this.geom = geom;
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
        setVertexParentHyperEdge(connectedVertices);
        this.connectedVertices = connectedVertices;
    }

    public enum EdgeLabel {
        S, B, I, F
    }

    private void setVertexParentHyperEdge(List<Vertex> connectedVertices) {
        connectedVertices.forEach(vertex -> vertex.getHyperEdges().add(this));
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
