package pl.edu.agh.gg.adaptation;

import pl.edu.agh.gg.FakeImage;
import pl.edu.agh.gg.HyperGraph;
import pl.edu.agh.gg.approximation.ElementApproxError;
import pl.edu.agh.gg.domain.VertexLike;
import pl.edu.agh.gg.domain.hyperEdge.HyperEdge;
import pl.edu.agh.gg.domain.hyperEdge.HyperEdgeB;
import pl.edu.agh.gg.domain.hyperEdge.HyperEdgeF;
import pl.edu.agh.gg.domain.hyperEdge.HyperEdgeI;
import pl.edu.agh.gg.productions.*;

import java.util.List;
import java.util.stream.Collectors;

public class Task16 {
    private List<HyperEdgeI> hyperEdgesI;

    public Task16(List<HyperEdgeI> hyperEdgesI) {
        this.hyperEdgesI = hyperEdgesI;
    }

    public void markHyperedgesForAdaptation(HyperGraph hyperGraph, double eps, long maxStep) {

        for (long i = 0; i < maxStep; i++) {
            // first loop
            boolean errorBiggerThanEps = false;
            ElementApproxError elementApproxError = new ElementApproxError(new FakeImage());
            for (HyperEdgeI hyperEdgeI : hyperEdgesI) {
                double approxError = elementApproxError.calculateError(hyperEdgeI);
                if (approxError > eps) {
                    errorBiggerThanEps = true;
                    P5 p5 = new P5(new FakeImage(), hyperGraph);
                    try {
                        p5.apply(hyperEdgeI);
                    } catch (CannotApplyProductionException ignore) {
                    }
                }
            }
            if (!errorBiggerThanEps) {
                return;
            }

            List<HyperEdgeB> bEdges = hyperGraph.vertexSet().stream()
                    .filter(VertexLike::isEdge)
                    .map(v -> v.getAsEdge().get())
                    .filter(e -> e.getEdgeLabel() == HyperEdge.EdgeLabel.B)
                    .map(e -> (HyperEdgeB) e)
                    .collect(Collectors.toList());

            List<HyperEdgeF> fEdges = hyperGraph.vertexSet().stream()
                    .filter(VertexLike::isEdge)
                    .map(v -> v.getAsEdge().get())
                    .filter(e -> e.getEdgeLabel() == HyperEdge.EdgeLabel.F)
                    .map(e -> (HyperEdgeF) e)
                    .collect(Collectors.toList());

            boolean anyProductionWasStarted = false;
            do {
                // second loop
                for (HyperEdgeI hyperEdgeI : hyperEdgesI) {
                    P2 p2 = new P2(new FakeImage(), hyperGraph);
                    try {
                        p2.apply(hyperEdgeI);
                    } catch (CannotApplyProductionException e) {
                        continue;
                    }
                    anyProductionWasStarted = true;
                }
                // third loop
                for (HyperEdgeB hyperEdgeB : bEdges) {
                    P3 p3 = new P3(new FakeImage(), hyperGraph);
                    try {
                        p3.apply(hyperEdgeB);
                    } catch (CannotApplyProductionException e) {
                        continue;
                    }
                    anyProductionWasStarted = true;
                }
                // forth loop
                for (HyperEdgeF hyperEdgeF : fEdges) {
                    P4 p4 = new P4(new FakeImage(), hyperGraph);
                    try {
                        p4.apply(hyperEdgeF);
                    } catch (CannotApplyProductionException e) {
                        continue;
                    }
                    anyProductionWasStarted = true;
                }

            } while (anyProductionWasStarted);
        }
    }
}
