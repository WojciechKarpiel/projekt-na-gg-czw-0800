package pl.edu.agh.gg.domain;

public class Vertex extends VertexLike {
    private final Geom geom;
    private final Rgb rgb;
    private Label label;

    public Vertex(Geom geom, Rgb rgb, Label label) {
        super(EntityType.VERTEX);
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
