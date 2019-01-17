package pl.edu.agh.gg.util;

import pl.edu.agh.gg.domain.Vertex;
import pl.edu.agh.gg.domain.hyperEdge.HyperEdge;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class EdgeUtils {

    private EdgeUtils() {
        throw new UnsupportedOperationException();
    }

    public static int calculateSideLength(HyperEdge e) {
        Vertex v1 = e.getConnectedVertices().get(0);
        Optional<Vertex> vX = e.getConnectedVertices().stream()
                .filter(v -> v.getGeom().getX() == v1.getGeom().getX() && v.getGeom().getY() != v1.getGeom().getY())
                .findAny();
        Optional<Vertex> vY = e.getConnectedVertices().stream()
                .filter(v -> v.getGeom().getY() == v1.getGeom().getY() && v.getGeom().getX() != v1.getGeom().getX())
                .findAny();
        return vX.map(vertex -> Math.abs(vertex.getGeom().getY() - v1.getGeom().getY())).orElseGet(() -> vY.map(vertex -> Math.abs(vertex.getGeom().getX() - v1.getGeom().getX())).orElse(0));
    }

    public static Set<Vertex> findCommonVertices(HyperEdge e1, HyperEdge e2) {
        Set<Vertex> commonVertices = new HashSet<>(e1.getConnectedVertices());
        commonVertices.retainAll(e2.getConnectedVertices());
        return commonVertices;
    }

    public static Set<HyperEdge> findRelatedFaceEdges(HyperEdge edge, Vertex vertex) {
        return vertex.getHyperEdges().stream()
                .filter(e -> e.getEdgeLabel() == HyperEdge.EdgeLabel.F)
                .filter(e -> e.getConnectedVertices().contains(vertex))
                .collect(Collectors.toSet());
    }
}
