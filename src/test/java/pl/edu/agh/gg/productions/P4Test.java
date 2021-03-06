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

import java.util.*;

public class P4Test {

    private Vertex v5 = new Vertex(new Geom(40, 100), new Rgb(255, 200, 0), Vertex.Label.V);
    private Vertex v6 = new Vertex(new Geom(80, 50), new Rgb(255, 200, 0), Vertex.Label.V);
    private Vertex v7 = new Vertex(new Geom(40, 0), new Rgb(255, 200, 0), Vertex.Label.V);
    private Vertex v8 = new Vertex(new Geom(0, 50), new Rgb(255, 200, 0), Vertex.Label.V);

    private HyperEdgeI i_5_6 = new HyperEdgeI(Arrays.asList(v5, v6), false);
    private HyperEdgeI i_6_7 = new HyperEdgeI(Arrays.asList(v6, v7), false);
    private HyperEdgeI i_7_8 = new HyperEdgeI(Arrays.asList(v7, v8), false);
    private HyperEdgeI i_8_5 = new HyperEdgeI(Arrays.asList(v8, v5), false);

    private HyperEdgeF f2_8 = new HyperEdgeF(Collections.singletonList(v8));
    private HyperEdgeF f2_6 = new HyperEdgeF(Collections.singletonList(v6));

    private HyperEdgeF f1_5_7 = new HyperEdgeF(Arrays.asList(v5, v7));


    private P4 p4ToTest;
    private HyperGraph graph;

    @Before
    public void setUp() {
        graph = new HyperGraph();
        f1_5_7.setDirection(HyperEdgeF.Direction.UP);
        f2_8.setDirection(HyperEdgeF.Direction.RIGHT);
        f2_6.setDirection(HyperEdgeF.Direction.LEFT);

        graph.add(v5);
        graph.add(v6);
        graph.add(v7);
        graph.add(v8);

        graph.add(i_5_6);
        graph.add(i_6_7);
        graph.add(i_7_8);
        graph.add(i_8_5);

        graph.add(f2_8);
        graph.add(f2_6);

        graph.add(f1_5_7);
        p4ToTest = new P4(new FakeImage(), graph);
    }

    @Test(expected = CannotApplyProductionException.class)
    public void WhenCenterIsNotHyperEdge_ThrowException() {
        p4ToTest.apply(v5);
    }

    @Test(expected = CannotApplyProductionException.class)
    public void WhenCenterEdgeLabelIsNotF_ThrowException() {
        p4ToTest.apply(i_5_6);
    }

    @Test(expected = CannotApplyProductionException.class)
    public void WhenCenterConnectedVerticesIsNotTwo_ThrowException() {
        f1_5_7.setConnectedVertices(Arrays.asList(v5, v7, v8));
        p4ToTest.apply(f1_5_7);
    }

    @Test(expected = CannotApplyProductionException.class)
    public void WhenThereAreNotExavtly3PathsBetweenv5v7_ThrowException() {
        f1_5_7.setConnectedVertices(new ArrayList<>());
        p4ToTest.apply(f1_5_7);
    }

    @Test(expected = CannotApplyProductionException.class)
    public void WhenLeftF2IsNotPresent_ThrowException() {
        graph.removeVertex(f2_8);
        p4ToTest.apply(f1_5_7);
    }

    @Test(expected = CannotApplyProductionException.class)
    public void WhenRightF2IsNotPresent_ThrowException() {
        graph.removeVertex(f2_6);
        p4ToTest.apply(f1_5_7);
    }

    @Test
    public void WhenProperCenterChosen_NewVertexIsCorrectlyConnectedWithIEdges() {
        Assert.assertEquals(2, i_8_5.getConnectedVertices().size());
        Assert.assertEquals(2, i_5_6.getConnectedVertices().size());
        Assert.assertEquals(2, i_7_8.getConnectedVertices().size());
        Assert.assertEquals(2, i_6_7.getConnectedVertices().size());
        p4ToTest.apply(f1_5_7);
        Assert.assertEquals(3, i_8_5.getConnectedVertices().size());
        Assert.assertEquals(3, i_5_6.getConnectedVertices().size());
        Assert.assertEquals(3, i_7_8.getConnectedVertices().size());
        Assert.assertEquals(3, i_6_7.getConnectedVertices().size());
    }

    @Test
    public void WhenProperCenterChosen_NewVertexIsCorrectlyConnectedWithF2Edges() {
        Assert.assertEquals(1, f2_6.getConnectedVertices().size());
        Assert.assertEquals(1, f2_8.getConnectedVertices().size());
        p4ToTest.apply(f1_5_7);
        Assert.assertEquals(2, f2_6.getConnectedVertices().size());
        Assert.assertEquals(2, f2_8.getConnectedVertices().size());
    }

    @Test
    public void WhenProperCenterChosen_CenterF1EdgeIsRemoved() {
        Assert.assertEquals(true, graph.containsVertex(f1_5_7));
        p4ToTest.apply(f1_5_7);
        Assert.assertEquals(false, graph.containsVertex(f1_5_7));
    }

    @Test
    public void WhenProperCenterChosen_NewVertexIsAdded() {
        p4ToTest.apply(f1_5_7);
        List<Vertex> connected_vs = i_5_6.getConnectedVertices();
        connected_vs.remove(v5);
        connected_vs.remove(v6);
        Assert.assertEquals(1, connected_vs.size());
        Vertex newVertex = connected_vs.get(0);
        Assert.assertEquals(v5.getGeom().getX(), newVertex.getGeom().getX());
        Assert.assertEquals(v6.getGeom().getY(), newVertex.getGeom().getY());
    }

    @Test
    public void WhenSideVerticesHaveMoreF2s_OnlyProperAreConnected() {
        HyperEdgeF f2_8_up = addF2Neighbour(v8, HyperEdgeF.Direction.UP);
        HyperEdgeF f2_8_down = addF2Neighbour(v8, HyperEdgeF.Direction.BOTTOM);
        HyperEdgeF f2_8_left = addF2Neighbour(v8, HyperEdgeF.Direction.LEFT);

        HyperEdgeF f2_6_up = addF2Neighbour(v6, HyperEdgeF.Direction.UP);
        HyperEdgeF f2_6_down = addF2Neighbour(v6, HyperEdgeF.Direction.BOTTOM);
        HyperEdgeF f2_6_right = addF2Neighbour(v6, HyperEdgeF.Direction.RIGHT);

        p4ToTest.apply(f1_5_7);

        Assert.assertEquals(f2_8_up.getConnectedVertices().size(), 1);
        Assert.assertEquals(f2_8_down.getConnectedVertices().size(), 1);
        Assert.assertEquals(f2_8_left.getConnectedVertices().size(), 1);
        Assert.assertEquals(f2_8.getConnectedVertices().size(), 2);

        Assert.assertEquals(f2_6_up.getConnectedVertices().size(), 1);
        Assert.assertEquals(f2_6_down.getConnectedVertices().size(), 1);
        Assert.assertEquals(f2_6_right.getConnectedVertices().size(), 1);
        Assert.assertEquals(f2_6.getConnectedVertices().size(), 2);
    }

    private HyperEdgeF addF2Neighbour(Vertex neighbour, HyperEdgeF.Direction dir) {
        HyperEdgeF edgeF = new HyperEdgeF(Collections.singletonList(neighbour));
        edgeF.setDirection(dir);
        graph.add(edgeF);
        graph.addEdge(edgeF, neighbour);
        return edgeF;
    }
}
