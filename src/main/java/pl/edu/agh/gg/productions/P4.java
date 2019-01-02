package pl.edu.agh.gg.productions;

import org.jgrapht.GraphPath;
import org.jgrapht.Graphs;
import org.jgrapht.alg.interfaces.KShortestPathAlgorithm;
import org.jgrapht.alg.shortestpath.KShortestSimplePaths;
import org.jgrapht.graph.DefaultEdge;
import pl.edu.agh.gg.HyperGraph;
import pl.edu.agh.gg.domain.Vertex;
import pl.edu.agh.gg.domain.VertexLike;
import pl.edu.agh.gg.domain.hyperEdge.HyperEdge;
import pl.edu.agh.gg.domain.hyperEdge.HyperEdgeF;

import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Optional;


/*
    Sytuacja, w której można zastosować tę produkcję nastąpi, gdy w 2 sąsiednich
    prostokątach (takich jak wynik P1) zastosujemy P2
 */
public class P4 extends Production {
    public P4(BufferedImage image, HyperGraph graph) {
        super(image, graph);
    }

    @Override
    public void apply(VertexLike center) throws CannotApplyProductionException {
        HyperGraph graph = getGraph();
        HyperEdgeF centralHyperEdge = (HyperEdgeF) center.getAsEdge()
                .filter(e -> e.getEdgeLabel() == HyperEdge.EdgeLabel.F)
                .orElseThrow(CannotApplyProductionException::new);
        List<Vertex> centerVertices = centralHyperEdge.getConnectedVertices();
        if (centralHyperEdge.getConnectedVertices().size() != 2) {
            throw new CannotApplyProductionException();
        }
        // Numbers taken from the picture illustrating production.
        Vertex v5 = centerVertices.get(0);
        Vertex v7 = centerVertices.get(1);
        // TODO: possibly exclude the direct path using path validator
        KShortestPathAlgorithm<VertexLike, DefaultEdge> shortestPathAlg = new KShortestSimplePaths<>(graph, 4);
        List<GraphPath<VertexLike, DefaultEdge>> paths = shortestPathAlg.getPaths(v5, v7, 3);

        // The shortest path is via F (length 2), other two is via either 8 or 6
        if (paths.size() != 3) {
            throw new CannotApplyProductionException();
        }

        // TODO: more checking
        // left and right relate to the illustration, in reality it can be rotated n * 90 deg
        GraphPath<VertexLike, DefaultEdge> leftPath = paths.get(1);
        GraphPath<VertexLike, DefaultEdge> rightPath = paths.get(2);
        VertexLike v8 = leftPath.getVertexList().get(2);
        HyperEdge f2left = Graphs.<VertexLike, DefaultEdge>neighborListOf(graph, v8).stream()
                .map(VertexLike::getAsEdge)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .filter(e -> e.getEdgeLabel() == HyperEdge.EdgeLabel.F)
                .findFirst()
                .orElseThrow(CannotApplyProductionException::new);

        VertexLike v6 = rightPath.getVertexList().get(2);
        HyperEdge f2right = Graphs.<VertexLike, DefaultEdge>neighborListOf(graph, v6).stream()
                .map(VertexLike::getAsEdge)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .filter(e -> e.getEdgeLabel() == HyperEdge.EdgeLabel.F)
                .findFirst()
                .orElseThrow(CannotApplyProductionException::new);

        graph.removeVertex(center);
    }
}

