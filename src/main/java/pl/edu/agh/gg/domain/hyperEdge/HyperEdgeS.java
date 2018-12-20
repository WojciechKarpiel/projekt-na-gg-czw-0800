package pl.edu.agh.gg.domain.hyperEdge;

import pl.edu.agh.gg.domain.Vertex;

import java.util.List;

public class HyperEdgeS extends HyperEdge {
    public HyperEdgeS(List<Vertex> connectedVerices) {
        super(EdgeLabel.S, connectedVerices);
    }
}
