package pl.edu.agh.gg.productions;

import pl.edu.agh.gg.HyperGraph;
import pl.edu.agh.gg.domain.Vertex;
import pl.edu.agh.gg.domain.VertexLike;
import pl.edu.agh.gg.domain.hyperEdge.HyperEdge;
import pl.edu.agh.gg.domain.hyperEdge.HyperEdgeI;
import pl.edu.agh.gg.util.EdgeUtils;

import java.awt.image.BufferedImage;
import java.util.*;
import java.util.stream.Collectors;

public class P5 extends Production {

    P5(BufferedImage image, HyperGraph graph) {
        super(image, graph);
    }

    @Override
    public void apply(VertexLike center) throws CannotApplyProductionException {
        HyperGraph graph = getGraph();
        Optional<HyperEdge> hyperEdgeOpt = center.getAsEdge().filter(e -> e.getEdgeLabel() == HyperEdge.EdgeLabel.I &&
                graph.vertexSet().contains(e));

        if (!hyperEdgeOpt.isPresent()) throw new CannotApplyProductionException();
        HyperEdgeI hyperEdgeI = (HyperEdgeI) hyperEdgeOpt.get();
        if (hyperEdgeI.isBreak()) {
            throw new CannotApplyProductionException();
        }
        hyperEdgeI.setBreak(true);

        propagateBreakForNeighbours(graph, hyperEdgeI);
    }

    private void propagateBreakForNeighbours(HyperGraph graph, HyperEdgeI baseEdge) {
        int beseEdgeConnectedVertices = baseEdge.getConnectedVertices().size();
        if (beseEdgeConnectedVertices != 3 && beseEdgeConnectedVertices != 2) {
            return;
        }

        int baseEdgeSideLength = EdgeUtils.calculateSideLength(baseEdge);

        // getting all bigger edges of type I
        List<HyperEdgeI> biggerIEdges = graph.vertexSet().stream()
                .filter(VertexLike::isEdge)
                .map(v -> v.getAsEdge().get())
                .filter(e -> e.getEdgeLabel() == HyperEdge.EdgeLabel.I)
                .map(e -> (HyperEdgeI) e)
                .filter(e -> EdgeUtils.findCommonVertices(e, baseEdge).size() == 1)
                .filter(e -> EdgeUtils.calculateSideLength(e) > baseEdgeSideLength)
                .collect(Collectors.toList());

        // getting edges with face edge, excluding diagonally
        List<HyperEdgeI> possibleEdges = biggerIEdges.stream().filter(e -> {
            Vertex commonVertex = EdgeUtils.findCommonVertices(e, baseEdge).stream().findAny().get();

            long faceEdgesCount = EdgeUtils.findRelatedFaceEdges(graph, e, commonVertex).stream().filter(fe -> {
                Set<Vertex> baseEdgeVertices = new HashSet<>(baseEdge.getConnectedVertices());

                Set<Vertex> biggerEdgeVertices = new HashSet<>(e.getConnectedVertices());
                Integer minX = biggerEdgeVertices.stream()
                        .map(v -> v.getGeom().getX())
                        .min(Comparator.comparing(Integer::valueOf))
                        .get();
                Integer minY = biggerEdgeVertices.stream()
                        .map(v -> v.getGeom().getY())
                        .min(Comparator.comparing(Integer::valueOf))
                        .get();
                Integer maxX = biggerEdgeVertices.stream()
                        .map(v -> v.getGeom().getX())
                        .max(Comparator.comparing(Integer::valueOf))
                        .get();
                Integer maxY = biggerEdgeVertices.stream()
                        .map(v -> v.getGeom().getY())
                        .max(Comparator.comparing(Integer::valueOf))
                        .get();

                return baseEdgeVertices.stream()
                        .allMatch(ev -> (ev.getGeom().getX() >= minX && ev.getGeom().getX() <= maxX) || (ev.getGeom().getY() >= minY && ev.getGeom().getY() <= maxY));
            }).count();
            return faceEdgesCount == 1;
        }).collect(Collectors.toList());

        possibleEdges.forEach(e -> {
            P6 prod = new P6(getImage(), getGraph(), baseEdge);
            prod.apply(e);
            propagateBreakForNeighbours(graph, e);
        });
    }
}
