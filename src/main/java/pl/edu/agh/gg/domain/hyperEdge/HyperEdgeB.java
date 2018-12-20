package pl.edu.agh.gg.domain.hyperEdge;

import pl.edu.agh.gg.domain.Vertex;

import java.util.List;

public class HyperEdgeB extends HyperEdge {
    public HyperEdgeB(List<Vertex> connectedVerices) {
        super(EdgeLabel.B, connectedVerices);
    }
}
