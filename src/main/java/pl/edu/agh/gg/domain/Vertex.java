package pl.edu.agh.gg.domain;

import pl.edu.agh.gg.domain.hyperEdge.HyperEdge;

import java.util.LinkedList;
import java.util.List;

public class Vertex extends VertexLike {
    private final Geom geom;
    private final Rgb rgb;
    private Label label;
    private List<HyperEdge> hyperEdges;

    public Vertex(Geom geom, Rgb rgb, Label label) {
        super(EntityType.VERTEX);
        this.hyperEdges = new LinkedList<>();
        this.geom = geom;
        this.rgb = rgb;
        this.label = label;
    }

    public enum Label {
        V;
    }


    public Geom getGeom() {
        return geom;
    }

    public Rgb getRgb() {
        return rgb;
    }

    public List<HyperEdge> getHyperEdges() {
        return hyperEdges;
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Vertex vertex = (Vertex) o;
//        return Objects.equals(geom, vertex.geom) &&
//                Objects.equals(rgb, vertex.rgb);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(geom, rgb);
//    }

    @Override
    public String toString() {
        return "Vertex{" +
                "geom=" + geom +
                ", rgb=" + rgb +
                ", id=" + getId() +
                '}';
    }
}
