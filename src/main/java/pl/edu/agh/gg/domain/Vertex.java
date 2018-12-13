package pl.edu.agh.gg.domain;

public class Vertex extends VertexLike {
    private final Position position;
    private final Rgb rgb;

    public Vertex(Position position, Rgb rgb) {
        super(EntityType.VERTEX);
        this.position = position;
        this.rgb = rgb;
    }

    public Position getPosition() {
        return position;
    }

    public Rgb getRgb() {
        return rgb;
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Vertex vertex = (Vertex) o;
//        return Objects.equals(position, vertex.position) &&
//                Objects.equals(rgb, vertex.rgb);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(position, rgb);
//    }

    @Override
    public String toString() {
        return "Vertex{" +
                "position=" + position +
                ", rgb=" + rgb +
                ", id=" + getId() +
                '}';
    }
}
