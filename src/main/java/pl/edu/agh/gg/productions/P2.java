package pl.edu.agh.gg.productions;

import pl.edu.agh.gg.HyperGraph;
import pl.edu.agh.gg.domain.Geom;
import pl.edu.agh.gg.domain.Vertex;
import pl.edu.agh.gg.domain.VertexLike;
import pl.edu.agh.gg.domain.hyperEdge.HyperEdge;
import pl.edu.agh.gg.domain.hyperEdge.HyperEdgeF;
import pl.edu.agh.gg.domain.hyperEdge.HyperEdgeI;

import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class P2 extends Production {

    public P2(BufferedImage image, HyperGraph graph) {
        super(image, graph);
    }

    @Override
    public void apply(VertexLike center) throws CannotApplyProductionException {
        HyperGraph graph = getGraph();
        Optional<HyperEdge> iOpt = center.getAsEdge().filter(e -> e.getEdgeLabel() == HyperEdge.EdgeLabel.I &&
                graph.vertexSet().contains(e));
        if (!iOpt.isPresent()) throw new CannotApplyProductionException();
        HyperEdge edge = iOpt.get();

        List<Vertex> connectedVertices = edge.getConnectedVertices();
        if (connectedVertices.size() != 4) throw new CannotApplyProductionException();
        int x1 = connectedVertices.stream().map(Vertex::getGeom).mapToInt(Geom::getX).min().getAsInt();
        int x2 = connectedVertices.stream().map(Vertex::getGeom).mapToInt(Geom::getX).max().getAsInt();
        int y1 = connectedVertices.stream().map(Vertex::getGeom).mapToInt(Geom::getY).min().getAsInt();
        int y2 = connectedVertices.stream().map(Vertex::getGeom).mapToInt(Geom::getY).max().getAsInt();
        int x = (x1 + x2) / 2;
        int y = (y1 + y2) / 2;
        Vertex v = new Vertex(new Geom(x, y), getRgb(x, y), Vertex.Label.V);

        connectedVertices.forEach(vertex -> graph.add(new HyperEdgeI(Arrays.asList(vertex, v), false)));

        HyperEdgeF fUp = new HyperEdgeF(Arrays.asList(v));
        HyperEdgeF fBottom = new HyperEdgeF(Arrays.asList(v));
        HyperEdgeF fLeft = new HyperEdgeF(Arrays.asList(v));
        HyperEdgeF fRight = new HyperEdgeF(Arrays.asList(v));

        fUp.setDirection(HyperEdgeF.Direction.UP);
        fBottom.setDirection(HyperEdgeF.Direction.BOTTOM);
        fLeft.setDirection(HyperEdgeF.Direction.LEFT);
        fRight.setDirection(HyperEdgeF.Direction.RIGHT);

        graph.add(fUp);
        graph.add(fBottom);
        graph.add(fLeft);
        graph.add(fRight);

        graph.removeEdge(edge);
    }
}
