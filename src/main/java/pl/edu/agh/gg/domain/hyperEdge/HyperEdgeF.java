package pl.edu.agh.gg.domain.hyperEdge;

import pl.edu.agh.gg.domain.Vertex;

import java.util.List;

import static pl.edu.agh.gg.domain.hyperEdge.HyperEdge.EdgeLabel.F;

public class HyperEdgeF extends HyperEdge {
    private Direction direction;

    public HyperEdgeF(List<Vertex> connectedVerices) {
        super(F, connectedVerices);
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public enum Direction {
        UP, RIGHT, LEFT, BOTTOM;
    }
}
