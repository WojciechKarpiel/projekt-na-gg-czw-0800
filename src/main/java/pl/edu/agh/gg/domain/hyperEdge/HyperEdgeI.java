package pl.edu.agh.gg.domain.hyperEdge;

import pl.edu.agh.gg.domain.Vertex;

import java.util.List;

import static pl.edu.agh.gg.domain.hyperEdge.HyperEdge.EdgeLabel.I;


public class HyperEdgeI extends HyperEdge {
    private boolean break_;

    public HyperEdgeI(List<Vertex> connectedVerices, boolean break_) {
        super(I, connectedVerices);
        this.break_ = break_;
    }

    public boolean isBreak() {
        return break_;
    }

    public void setBreak(boolean break_) {
        this.break_ = break_;
    }
}

