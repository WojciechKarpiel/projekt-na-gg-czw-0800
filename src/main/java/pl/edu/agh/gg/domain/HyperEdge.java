package pl.edu.agh.gg.domain;

public class HyperEdge extends VertexLike {
    private EdgeType edgeType;

    public HyperEdge(EdgeType edgeType) {
        super(EntityType.EDGE);
        this.edgeType = edgeType;
    }

    public EdgeType getEdgeType() {
        return edgeType;
    }

    public static enum EdgeType {
        S, B, I_NOBREAK, I_BREAK, F1, F2, F3, F4;
    }

    // Nie można robić takiego `equals` bo wtedy doda sie tylko jedna krawędź danego typu :(
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        HyperEdge hyperEdge = (HyperEdge) o;
//        return edgeType == hyperEdge.edgeType;
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(edgeType);
//    }

    @Override
    public String toString() {
        return "HyperEdge{" +
                "edgeType=" + edgeType +
                ", id=" + getId() +
                '}';
    }
}
