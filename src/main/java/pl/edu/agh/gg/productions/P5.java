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

@SuppressWarnings("OptionalGetWithoutIsPresent")
public class P5 extends Production {

    public P5(BufferedImage image, HyperGraph graph) {
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

    private void propagateBreakForNeighbours(HyperGraph graph, HyperEdgeI smallEdge) {
        int beseEdgeConnectedVertices = smallEdge.getConnectedVertices().size();
        if (beseEdgeConnectedVertices != 3 && beseEdgeConnectedVertices != 2) {
            return;
        }

        List<HyperEdgeI> bigEdges = graph.vertexSet().stream()
                // getting all edges of type I
                .filter(VertexLike::isEdge)
                .map(v -> v.getAsEdge().get())
                .filter(e -> e.getEdgeLabel() == HyperEdge.EdgeLabel.I)
                .map(e -> (HyperEdgeI) e)
                // excluding edges without one common vertex
                .filter(e -> EdgeUtils.findCommonVertices(e, smallEdge).size() == 1)
                // excluding edges with smaller side
                .filter(e -> EdgeUtils.calculateSideLength(e) > EdgeUtils.calculateSideLength(smallEdge))
                // excluding diagonal edges
                .filter(bigEdge -> {
                    Set<Vertex> smallEdgeVertices = new HashSet<>(smallEdge.getConnectedVertices());
                    Set<Vertex> bigEdgeVertices = new HashSet<>(bigEdge.getConnectedVertices());

                    Integer minX = bigEdgeVertices.stream()
                            .map(v -> v.getGeom().getX())
                            .min(Comparator.comparing(Integer::valueOf))
                            .get();
                    Integer minY = bigEdgeVertices.stream()
                            .map(v -> v.getGeom().getY())
                            .min(Comparator.comparing(Integer::valueOf))
                            .get();
                    Integer maxX = bigEdgeVertices.stream()
                            .map(v -> v.getGeom().getX())
                            .max(Comparator.comparing(Integer::valueOf))
                            .get();
                    Integer maxY = bigEdgeVertices.stream()
                            .map(v -> v.getGeom().getY())
                            .max(Comparator.comparing(Integer::valueOf))
                            .get();

                    return smallEdgeVertices.stream()
                            .allMatch(ev -> (ev.getGeom().getX() >= minX && ev.getGeom().getX() <= maxX) || (ev.getGeom().getY() >= minY && ev.getGeom().getY() <= maxY));
                })
                // excluding edges without F edge between
                .filter(bigEdge -> {
                    Vertex commonVertex = EdgeUtils.findCommonVertices(bigEdge, smallEdge).stream().findAny().get();
                    Optional<HyperEdge> any = EdgeUtils.findRelatedFaceEdges(graph, bigEdge, commonVertex).stream().findAny();
                    return any.isPresent();
                }).collect(Collectors.toList());

        bigEdges.forEach(possibleEdge -> {
            possibleEdge.setBreak(true);
            propagateBreakForNeighbours(graph, possibleEdge);
        });
    }
}
