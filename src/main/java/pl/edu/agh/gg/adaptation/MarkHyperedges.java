package pl.edu.agh.gg.adaptation;

import pl.edu.agh.gg.FakeImage;
import pl.edu.agh.gg.HyperGraph;
import pl.edu.agh.gg.approximation.ElementApproxError;
import pl.edu.agh.gg.domain.hyperEdge.HyperEdge;
import pl.edu.agh.gg.domain.hyperEdge.HyperEdgeI;
import pl.edu.agh.gg.productions.CannotApplyProductionException;
import pl.edu.agh.gg.productions.P2;
import pl.edu.agh.gg.productions.P5;

import java.util.List;

public class MarkHyperedges {
    private List<HyperEdgeI> hyperEdgesI;

    public MarkHyperedges(List<HyperEdgeI> hyperEdgesI){
        this.hyperEdgesI = hyperEdgesI;
    }

    public void markHyperedgesForAdaptation(HyperGraph hyperGraph, double eps, long maxStep){

        for(long i = 0; i < maxStep; i++){
            // first loop
            boolean errorBiggerThanEps = false;
            ElementApproxError elementApproxError = new ElementApproxError(new FakeImage());
            for(HyperEdgeI hyperEdgeI: hyperEdgesI){
                double approxError = elementApproxError.calculateError(hyperEdgeI);
                if(approxError > eps){
                    errorBiggerThanEps = true;
                    P5 p5 = new P5(new FakeImage(), hyperGraph);
                    try {
                        p5.apply(hyperEdgeI);
                    } catch (CannotApplyProductionException e){
  
                    }
                }
            }
            if(!errorBiggerThanEps){
                return;
            }

            boolean anyProductionWasStarted = false;
            do{
                // second loop
                for(HyperEdgeI hyperEdgeI: hyperEdgesI){
                    P2 p2 = new P2(new FakeImage(), hyperGraph);
                    try {
                        p2.apply(hyperEdgeI);
                    } catch (CannotApplyProductionException e){
                        continue;
                    }
                    anyProductionWasStarted = true;
                }
                // TODO: third and forth loop
            }while (anyProductionWasStarted);
        }
    }
}
