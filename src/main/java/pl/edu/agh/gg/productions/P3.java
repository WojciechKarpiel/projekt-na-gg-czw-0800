package pl.edu.agh.gg.productions;

import org.jgrapht.Graphs;
import pl.edu.agh.gg.HyperGraph;
import pl.edu.agh.gg.domain.Geom;
import pl.edu.agh.gg.domain.Vertex;
import pl.edu.agh.gg.domain.VertexLike;
import pl.edu.agh.gg.domain.hyperEdge.HyperEdge;
import pl.edu.agh.gg.domain.hyperEdge.HyperEdgeB;
import pl.edu.agh.gg.domain.hyperEdge.HyperEdgeI;
import pl.edu.agh.gg.domain.hyperEdge.HyperEdgeF;

import java.awt.image.BufferedImage;
import java.util.*;
import java.util.stream.Collectors;

import static pl.edu.agh.gg.domain.hyperEdge.HyperEdge.*;


public class P3 extends Production {
    public P3(BufferedImage image, HyperGraph graph) {
        super(image, graph);
    }

    @Override
    public void apply(VertexLike center) throws CannotApplyProductionException {
        HyperGraph graph = getGraph();
        HyperEdgeB b;
        Optional<HyperEdge> optB = center.getAsEdge();
        if (!optB.isPresent() || optB.get().getEdgeLabel() != EdgeLabel.B ||
                optB.get().getConnectedVertices().size() != 2) {
            throw new CannotApplyProductionException();
        } else {
            b = (HyperEdgeB)optB.get();
        }

        // Two edge vertices
        Vertex bVertex1 = b.getConnectedVertices().get(0);
        Vertex bVertex2 = b.getConnectedVertices().get(1);

        // Searching for the third vertex and corresponding I edges
        Vertex thirdVertex = null;
        HyperEdgeI iEdge1 = null, iEdge2 = null;
        for (HyperEdgeI e1 : getIEdges(bVertex1)) {
            for (HyperEdgeI e2 : getIEdges(bVertex2)) {
                // Third vertex is an intersection of vertices connected to two proper I edges
                List<Vertex> iEdgeVertices = new ArrayList<>(e1.getConnectedVertices());
                iEdgeVertices.retainAll(e2.getConnectedVertices());
                if (iEdgeVertices.size() == 1) {
                    thirdVertex = iEdgeVertices.get(0);
                    iEdge1 = e1;
                    iEdge2 = e2;
                    break;
                }
            }
            if (thirdVertex != null) {
                break;
            }
        }
        if (thirdVertex == null) {
            throw new CannotApplyProductionException();
        }

        // Getting set of F edges connected to the third vertex
        Set<HyperEdgeF> fEdges = new HashSet<>();
        for (VertexLike thirdVNeighbour : Graphs.neighborSetOf(graph, thirdVertex)) {
            if (thirdVNeighbour.isEdge()) {
                HyperEdge edge = thirdVNeighbour.getAsEdge().get();
                if (edge.getEdgeLabel() == EdgeLabel.F) {
                    HyperEdgeF edgeF = (HyperEdgeF)edge;
                    // F edge with two connected vertices cannot be expanded using P3
                    if (edgeF.getConnectedVertices().size() == 1) {
                        fEdges.add((HyperEdgeF) edge);
                    }
                }
            }
        }
        if (fEdges.isEmpty()) {
            throw new CannotApplyProductionException();
        }

        // Looking for proper fEdge
        HyperEdgeF fEdge = null;
        // First we need to check, which direction is correct for our vertices
        HyperEdgeF.Direction fEdgeDirection;
        if (bVertex1.getGeom().getX() - bVertex2.getGeom().getX() != 0) {
            if (thirdVertex.getGeom().getY() < bVertex1.getGeom().getY()) {
                fEdgeDirection = HyperEdgeF.Direction.UP;
            } else {
                fEdgeDirection = HyperEdgeF.Direction.BOTTOM;
            }
        } else {
            if (thirdVertex.getGeom().getX() > bVertex1.getGeom().getX()) {
                fEdgeDirection = HyperEdgeF.Direction.LEFT;
            } else {
                fEdgeDirection = HyperEdgeF.Direction.RIGHT;
            }
        }

        // Looking for F edge with proper direction
        for (HyperEdgeF fe : fEdges) {
            if (fe.getDirection() == fEdgeDirection) {
                fEdge = fe;
                break;
            }
        }
        if (fEdge == null) {
            throw new CannotApplyProductionException();
        }

        // Creating new vertex in the middle of the border edge
        int newVertexX = (bVertex1.getGeom().getX() + bVertex2.getGeom().getX()) / 2;
        int newVertexY = (bVertex1.getGeom().getY() + bVertex2.getGeom().getY()) / 2;
        Vertex newVertex = new Vertex(
                new Geom(newVertexX, newVertexY),
                getRgb(newVertexX, newVertexY),
                Vertex.Label.V
        );
        graph.add(newVertex);

        // Adding new vertex to the F edge
        fEdge.setConnectedVertices(Arrays.asList(thirdVertex, newVertex));
        graph.add(fEdge);

        // Adding new vertex to I edges
        for (HyperEdgeI iEdge : Arrays.asList(iEdge1, iEdge2)) {
            List<Vertex> vertices = new ArrayList<>(iEdge.getConnectedVertices());
            vertices.add(newVertex);
            iEdge.setConnectedVertices(vertices);
            graph.add(iEdge);
        }

        // Removing existing B edge
        graph.removeVertex(b);

        // Creating two new B edges
        HyperEdgeB firstBEdge = new HyperEdgeB(Arrays.asList(bVertex1, newVertex));
        HyperEdgeB secondBEdge = new HyperEdgeB(Arrays.asList(bVertex2, newVertex));

        // Attaching B edges
        graph.add(firstBEdge);
        graph.add(secondBEdge);
    }

    private Set<HyperEdgeI> getIEdges(VertexLike v) {
        return Graphs.neighborSetOf(this.getGraph(), v)
                .stream()
                .filter(vertexLike ->
                        vertexLike.isEdge() && ((HyperEdge)vertexLike).getEdgeLabel() == EdgeLabel.I
                )
                .map(vertexLike -> (HyperEdgeI)vertexLike)
                .collect(Collectors.toSet());
    }
}
