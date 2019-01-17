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

        if (v5.getGeom().getY() < v7.getGeom().getY()) {
            Vertex tmp = v5;
            v5 = v7;
            v7 = tmp;
        }
        // TODO: possibly exclude the direct path using path validator
        KShortestPathAlgorithm<VertexLike, DefaultEdge> shortestPathAlg = new KShortestSimplePaths<>(graph, 4);
        List<GraphPath<VertexLike, DefaultEdge>> paths = shortestPathAlg.getPaths(v5, v7, 3);

        // The shortest path is via F (length 2), other two is via either 8 or 6
        if (paths.size() != 3) {
            throw new CannotApplyProductionException();
        }

        // left and right relate to the illustration, in reality it can be rotated n * 90 deg
        GraphPath<VertexLike, DefaultEdge> leftPath = paths.get(1);
        GraphPath<VertexLike, DefaultEdge> rightPath = paths.get(2);

        Vertex a = leftPath.getVertexList().get(2).getAsVertex().orElseThrow(CannotApplyProductionException::new);
        Vertex b = rightPath.getVertexList().get(2).getAsVertex().orElseThrow(CannotApplyProductionException::new);

        if (a.getGeom().getX() > b.getGeom().getX()) {
            GraphPath<VertexLike, DefaultEdge> tmp = leftPath;
            leftPath = rightPath;
            rightPath = tmp;
        }

        VertexLike v8 = leftPath.getVertexList().get(2);
        HyperEdge f2left = getHyperEdgeF(v8, HyperEdgeF.Direction.RIGHT);

        VertexLike v6 = rightPath.getVertexList().get(2);
        HyperEdge f2right = getHyperEdgeF(v6, HyperEdgeF.Direction.LEFT);

        graph.removeVertex(center);

        int x1 = v5.getGeom().getX();
        int y3 = v6.getAsVertex().orElseThrow(CannotApplyProductionException::new).getGeom().getY();
        Vertex v = new Vertex(new Geom(x1, y3), getRgb(x1, y3), Vertex.Label.V);
        graph.add(v);

        HyperEdgeF f1up = new HyperEdgeF(Arrays.asList(v5, v));
        f1up.setDirection(HyperEdgeF.Direction.UP);
        graph.add(f1up);
        graph.addEdge(v5, f1up);
        graph.addEdge(v, f1up);

        HyperEdgeF f1down = new HyperEdgeF(Arrays.asList(v7, v));
        f1down.setDirection(HyperEdgeF.Direction.BOTTOM);
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
        connectIToCenter(leftPath, 1, v);
        // bottom left corner
        connectIToCenter(leftPath, 3, v);
        // top right corner
        connectIToCenter(rightPath, 1, v);
        // bottom right corner
        connectIToCenter(rightPath, 3, v);
    }

    private void connectIToCenter(GraphPath<VertexLike, DefaultEdge> path, int i_index, Vertex center) {
        path.getVertexList().get(i_index).getAsEdge().map(e -> {
            getGraph().addEdge(e, center);
            e.setConnectedVertices(updatedVertices(e.getConnectedVertices(), center));
            return e;
        });
    }

    private HyperEdgeF getHyperEdgeF(VertexLike v6, HyperEdgeF.Direction left) {
        return Graphs.neighborListOf(getGraph(), v6).stream()
                .map(VertexLike::getAsEdge)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .filter(e -> e.getEdgeLabel() == HyperEdge.EdgeLabel.F)
                .map(e -> (HyperEdgeF) e)
                .filter(e -> e.getDirection() == left)
                .findFirst()
                .orElseThrow(CannotApplyProductionException::new);
    }

    private List<Vertex> updatedVertices(List<Vertex> connVertices, Vertex v) {
        List<Vertex> vertices = new ArrayList<>(connVertices.size() + 1);
        vertices.addAll(connVertices);
        vertices.add(v);
        return vertices;
    }

}

