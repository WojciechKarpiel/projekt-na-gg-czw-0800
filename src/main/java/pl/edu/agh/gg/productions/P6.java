package pl.edu.agh.gg.productions;

import pl.edu.agh.gg.HyperGraph;
import pl.edu.agh.gg.domain.Vertex;
import pl.edu.agh.gg.domain.VertexLike;
import pl.edu.agh.gg.domain.hyperEdge.HyperEdge;
import pl.edu.agh.gg.domain.hyperEdge.HyperEdgeI;
import pl.edu.agh.gg.util.EdgeUtils;

import java.awt.image.BufferedImage;
import java.util.Set;

public class P6 extends Production {

    private HyperEdgeI smallEdgeI;

    P6(BufferedImage image, HyperGraph graph, HyperEdgeI smallEdgeI) {
        super(image, graph);
        this.smallEdgeI = smallEdgeI;
    }

    @Override
    public void apply(VertexLike bigEdge) throws CannotApplyProductionException {
        if (!bigEdge.isEdge() || bigEdge.getAsEdge().get().getEdgeLabel() != HyperEdge.EdgeLabel.I || smallEdgeI.getEdgeLabel() != HyperEdge.EdgeLabel.I) {
            throw new CannotApplyProductionException();
        }
        HyperEdgeI bigEdgeI = (HyperEdgeI) bigEdge.getAsEdge().get();


        if (!smallEdgeI.isBreak()) {
            throw new CannotApplyProductionException();
        }

        if (smallEdgeI.getConnectedVertices().size() != 3) {
            throw new CannotApplyProductionException();
        }
        Set<Vertex> commonVertices = EdgeUtils.findCommonVertices(smallEdgeI, bigEdgeI);
        if (commonVertices.size() != 1) {
            throw new CannotApplyProductionException();
        }

        bigEdgeI.setBreak(true);
    }
}
