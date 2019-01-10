package pl.edu.agh.gg.productions;

import org.jgrapht.GraphPath;
import org.jgrapht.Graphs;
import org.jgrapht.alg.interfaces.KShortestPathAlgorithm;
import org.jgrapht.alg.shortestpath.KShortestSimplePaths;
import org.jgrapht.graph.DefaultEdge;
import pl.edu.agh.gg.HyperGraph;
import pl.edu.agh.gg.domain.Geom;
import pl.edu.agh.gg.domain.Vertex;
import pl.edu.agh.gg.domain.VertexLike;
import pl.edu.agh.gg.domain.hyperEdge.HyperEdge;
import pl.edu.agh.gg.domain.hyperEdge.HyperEdgeF;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
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

        int x1 = v5.getGeom().getX();
        int y3 = v6.getAsVertex().get().getGeom().getY();
        Vertex v = new Vertex(new Geom(x1, y3), getRgb(x1, y3), Vertex.Label.V);
        graph.add(v);

        HyperEdgeF f1up = new HyperEdgeF(Arrays.asList(v5, v));
        graph.add(f1up);
        graph.addEdge(v5, f1up);
        graph.addEdge(v, f1up);

        HyperEdgeF f1down = new HyperEdgeF(Arrays.asList(v7, v));
        graph.add(f1down);
        graph.addEdge(v7, f1down);
        graph.addEdge(v, f1down);

        List<Vertex> connectedVertices = f2left.getConnectedVertices();
        f2left.setConnectedVertices(updatedVertices(connectedVertices, v));
        graph.addEdge(f2left, v);

        connectedVertices = f2right.getConnectedVertices();
        f2right.setConnectedVertices(updatedVertices(connectedVertices, v));
        graph.addEdge(f2right, v);

        // connect center with I Hyper edges
        // top left corner
        leftPath.getVertexList().get(1).getAsEdge().map(e -> {
            graph.addEdge(e, v);
            e.setConnectedVertices(updatedVertices(e.getConnectedVertices(), v));
            return e;
        });
        // bottom left corner
        leftPath.getVertexList().get(3).getAsEdge().map(e -> {
            graph.addEdge(e, v);
            e.setConnectedVertices(updatedVertices(e.getConnectedVertices(), v));
            return e;
        });
        // top right corner
        rightPath.getVertexList().get(1).getAsEdge().map(e -> {
            graph.addEdge(e, v);
            e.setConnectedVertices(updatedVertices(e.getConnectedVertices(), v));
            return e;
        });
        // bottom right corner
        rightPath.getVertexList().get(3).getAsEdge().map(e -> {
            graph.addEdge(e, v);
            e.setConnectedVertices(updatedVertices(e.getConnectedVertices(), v));
            return e;
        });
    }

    private List<Vertex> updatedVertices(List<Vertex> connVertices, Vertex v) {
        List<Vertex> vertices = new ArrayList<>(connVertices.size() + 1);
        vertices.addAll(connVertices);
        vertices.add(v);
        return vertices;
    }
}

