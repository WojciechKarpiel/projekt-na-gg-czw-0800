package pl.edu.agh.gg.productions;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pl.edu.agh.gg.FakeImage;
import pl.edu.agh.gg.HyperGraph;
import pl.edu.agh.gg.domain.Geom;
import pl.edu.agh.gg.domain.Rgb;
import pl.edu.agh.gg.domain.Vertex;
import pl.edu.agh.gg.domain.VertexLike;
import pl.edu.agh.gg.domain.hyperEdge.*;

import java.util.*;
import java.util.stream.Collectors;

public class P2Test {

    private Vertex v1 = new Vertex(new Geom(0, 100), new Rgb(255, 200, 0), Vertex.Label.V);
    private Vertex v2 = new Vertex(new Geom(100, 0), new Rgb(255, 200, 0), Vertex.Label.V);
    private Vertex v3 = new Vertex(new Geom(100, 100), new Rgb(255, 200, 0), Vertex.Label.V);
    private Vertex v4 = new Vertex(new Geom(0, 0), new Rgb(255, 200, 0), Vertex.Label.V);

    private HyperEdgeB b1 = new HyperEdgeB(Arrays.asList(v1, v2));
    private HyperEdgeS s1 = new HyperEdgeS(Arrays.asList(v1, v3, v2));
    private HyperEdgeF f1 = new HyperEdgeF(Collections.singletonList(v4));

    private HyperEdgeI empty = new HyperEdgeI(Collections.emptyList(), false);
    private HyperEdgeI threeVertex = new HyperEdgeI(Arrays.asList(v1, v3, v2), false);
    private HyperEdgeI fiveVertex = new HyperEdgeI(Arrays.asList(v1, v3, v2, v1, v2), false);
    private HyperEdgeI properEdge = new HyperEdgeI(Arrays.asList(v1, v2, v3, v4), false);

    private P2 p2Test;
    private HyperGraph graph;

    @Before
    public void setUp() {
        graph = new HyperGraph();
        graph.add(b1);
        graph.add(s1);
        graph.add(f1);

        graph.add(empty);
        graph.add(threeVertex);
        graph.add(fiveVertex);
        graph.add(properEdge);

        p2Test = new P2(new FakeImage(), graph);
    }

    @Test(expected = CannotApplyProductionException.class)
    public void whenEdgeIsLabeledB_ThrowException() {
        p2Test.apply(b1);
    }

    @Test(expected = CannotApplyProductionException.class)
    public void whenEdgeIsLabeledS_ThrowException() {
        p2Test.apply(s1);
    }

    @Test(expected = CannotApplyProductionException.class)
    public void whenEdgeIsLabeledF_ThrowException() {
        p2Test.apply(f1);
    }

    @Test(expected = CannotApplyProductionException.class)
    public void whenEdgeHasThreeConnectedVertices_ThrowException() {
        p2Test.apply(threeVertex);
    }

    @Test(expected = CannotApplyProductionException.class)
    public void whenEdgeHasFiveConnectedVertices_ThrowException() {
        p2Test.apply(fiveVertex);
    }

    @Test(expected = CannotApplyProductionException.class)
    public void whenEdgeHasNoConnectedVertices_ThrowException() {
        p2Test.apply(empty);
    }

    @Test
    public void afterProductionGraphShouldNotContainFirstEdge() {
        graph = new HyperGraph();
        graph.add(properEdge);
        p2Test = new P2(new FakeImage(), graph);

        p2Test.apply(properEdge);

        List<HyperEdge> hyperEdges = graph
                .vertexSet()
                .stream()
                .filter(VertexLike::isEdge)
                .map(e -> e.getAsEdge().get())
                .collect(Collectors.toList());

        Assert.assertFalse(hyperEdges.contains(properEdge));
    }

    @Test
    public void afterProductionGraphShouldContainVertexWithProperGenom() {
        p2Test.apply(properEdge);

        Optional<Geom> optionalGeom = graph
                .vertexSet()
                .stream()
                .filter(VertexLike::isVertex)
                .map(e -> e.getAsVertex().get())
                .filter(e -> !properEdge.getConnectedVertices().contains(e))
                .map(Vertex::getGeom)
                .filter(geom -> geom.getX() == 50 && geom.getY() == 50)
                .findAny();

        Assert.assertTrue(optionalGeom.isPresent());
    }

    @Test
    public void afterProductionGraphShouldContainEdgesWithLabelF() {
        graph = new HyperGraph();
        graph.add(properEdge);
        p2Test = new P2(new FakeImage(), graph);

        p2Test.apply(properEdge);

        List<HyperEdge> hyperEdgeFList = graph
                .vertexSet()
                .stream()
                .filter(VertexLike::isEdge)
                .map(e -> e.getAsEdge().get())
                .filter(edge -> !properEdge.getConnectedVertices().containsAll(edge.getConnectedVertices()))
                .filter(edge -> HyperEdge.EdgeLabel.F.equals(edge.getEdgeLabel()))
                .collect(Collectors.toList());


        Assert.assertEquals(hyperEdgeFList.size(), 4);

        List<HyperEdgeF.Direction> directionList = hyperEdgeFList
                        .stream()
                        .map(e -> (HyperEdgeF) e)
                        .map(HyperEdgeF::getDirection)
                        .collect(Collectors.toList());

        Assert.assertTrue(directionList.contains(HyperEdgeF.Direction.UP));
        Assert.assertTrue(directionList.contains(HyperEdgeF.Direction.BOTTOM));
        Assert.assertTrue(directionList.contains(HyperEdgeF.Direction.LEFT));
        Assert.assertTrue(directionList.contains(HyperEdgeF.Direction.RIGHT));
    }


    @Test
    public void afterProductionGraphShouldContainForEdgesWithLabelI() {
        graph = new HyperGraph();
        graph.add(properEdge);
        p2Test = new P2(new FakeImage(), graph);

        p2Test.apply(properEdge);

        List<HyperEdge> hyperEdgeIList = graph
                .vertexSet()
                .stream()
                .filter(VertexLike::isEdge)
                .map(e -> e.getAsEdge().get())
                .filter(edge -> HyperEdge.EdgeLabel.I.equals(edge.getEdgeLabel()))
                .collect(Collectors.toList());

        Assert.assertEquals(hyperEdgeIList.size(), 4);
    }
}
