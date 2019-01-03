package pl.edu.agh.gg.productions;

import org.jgrapht.Graphs;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pl.edu.agh.gg.FakeImage;
import pl.edu.agh.gg.HyperGraph;
import pl.edu.agh.gg.domain.Geom;
import pl.edu.agh.gg.domain.Rgb;
import pl.edu.agh.gg.domain.Vertex;
import pl.edu.agh.gg.domain.VertexLike;
import pl.edu.agh.gg.domain.hyperEdge.HyperEdge;
import pl.edu.agh.gg.domain.hyperEdge.HyperEdgeB;
import pl.edu.agh.gg.domain.hyperEdge.HyperEdgeF;
import pl.edu.agh.gg.domain.hyperEdge.HyperEdgeI;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class P3Test {

    private Vertex center = new Vertex(new Geom(50, 40), new Rgb(255, 200, 0), Vertex.Label.V);
    private Vertex v1 = new Vertex(new Geom(0, 80), new Rgb(255, 200, 0), Vertex.Label.V);
    private Vertex v2 = new Vertex(new Geom(100, 80), new Rgb(255, 200, 0), Vertex.Label.V);
    private Vertex v3 = new Vertex(new Geom(0, 0), new Rgb(255, 200, 0), Vertex.Label.V);
    private HyperEdgeB b1 = new HyperEdgeB(Arrays.asList(v1, v2));
    private HyperEdgeB b2 = new HyperEdgeB(Arrays.asList(v1, v3, v2));
    private HyperEdgeF f1 = new HyperEdgeF(Collections.singletonList(center));
    private HyperEdgeF f2 = new HyperEdgeF(Collections.singletonList(center));
    private HyperEdgeI i1 = new HyperEdgeI(Arrays.asList(v1, center), false);
    private HyperEdgeI i2 = new HyperEdgeI(Arrays.asList(v2, center), false);
    private HyperEdgeI i3 = new HyperEdgeI(Arrays.asList(v3, center), false);

    private P3 p3ToTest;
    private HyperGraph graph;

    @Before
    public void setUp() {
        graph = new HyperGraph();
        f1.setDirection(HyperEdgeF.Direction.UP);
        f2.setDirection(HyperEdgeF.Direction.LEFT);
        graph.add(center);
        graph.add(v1);
        graph.add(v2);
        graph.add(v3);
        graph.add(b1);
        graph.add(b2);
        graph.add(f2);
        graph.add(f1);
        graph.add(i1);
        graph.add(i2);
        graph.add(i3);
        p3ToTest = new P3(new FakeImage(), graph);
    }

    @Test(expected = CannotApplyProductionException.class)
    public void WhenCenterIsNotHyperEdge_ThrowException() {
        p3ToTest.apply(v1);
    }

    @Test(expected = CannotApplyProductionException.class)
    public void WhenCenterEdgeLabelIsNotB_ThrowException() {
        p3ToTest.apply(i1);
    }

    @Test(expected = CannotApplyProductionException.class)
    public void WhenCenterConnectedVerticesIsNotTwo_ThrowException() {
        p3ToTest.apply(b2);
    }

    @Test
    public void WhenProperCenterChosen_NewVertexCreatedHasProperProperties() {
        p3ToTest.apply(b1);
        List<Vertex> iEdgeVertices = i1.getConnectedVertices();
        iEdgeVertices.retainAll(i2.getConnectedVertices());
        Assert.assertEquals(2, iEdgeVertices.size());
        iEdgeVertices.remove(iEdgeVertices.indexOf(center));
        Vertex createdOne = iEdgeVertices.get(0);
        Assert.assertEquals(new Geom(50, 80), createdOne.getGeom());
        Assert.assertEquals(new Rgb(255, 200, 0), createdOne.getRgb());
    }

    @Test
    public void WhenProperCenterChosen_NewVertexIsCorrectlyConnectedWithExistingEdges() {
        Assert.assertEquals(2, i1.getConnectedVertices().size());
        Assert.assertEquals(2, i2.getConnectedVertices().size());
        Assert.assertEquals(1, f1.getConnectedVertices().size());
        p3ToTest.apply(b1);
        Assert.assertEquals(3, i1.getConnectedVertices().size());
        Assert.assertEquals(3, i2.getConnectedVertices().size());
        Assert.assertEquals(2, f1.getConnectedVertices().size());
    }

    @Test
    public void WhenProperCenterChosen_TwoNewHyperEdgesAreCreated() {
        p3ToTest.apply(b1);
        List<Vertex> iEdgeVertices = i1.getConnectedVertices();
        iEdgeVertices.retainAll(i2.getConnectedVertices());
        Assert.assertEquals(2, iEdgeVertices.size());
        iEdgeVertices.remove(iEdgeVertices.indexOf(center));
        Vertex createdOne = iEdgeVertices.get(0);

        List<VertexLike> createdOneNeighbors = Graphs.neighborListOf(graph, createdOne);
        createdOneNeighbors.remove(i1);
        createdOneNeighbors.remove(i2);
        createdOneNeighbors.remove(f1);
        Assert.assertEquals(2, createdOneNeighbors.size());

        Optional<HyperEdge> newB1 = createdOneNeighbors.get(0).getAsEdge();
        Assert.assertTrue(newB1.isPresent());
        Optional<HyperEdge> newB2 = createdOneNeighbors.get(1).getAsEdge();
        Assert.assertTrue(newB2.isPresent());

        HyperEdge newB1Edge = newB1.get();
        HyperEdge newB2Edge = newB2.get();
        Assert.assertTrue(
                newB1Edge.getConnectedVertices().contains(v1) && newB2Edge.getConnectedVertices().contains(v2)
                        || newB2Edge.getConnectedVertices().contains(v1) && newB1Edge.getConnectedVertices().contains(v2)
        );
        Assert.assertEquals(2, newB1Edge.getConnectedVertices().size());
        Assert.assertEquals(2, newB2Edge.getConnectedVertices().size());
    }

}
