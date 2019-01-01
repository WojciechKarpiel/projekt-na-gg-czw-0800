package pl.edu.agh.gg.productions;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pl.edu.agh.gg.FakeImage;
import pl.edu.agh.gg.HyperGraph;
import pl.edu.agh.gg.domain.Geom;
import pl.edu.agh.gg.domain.Rgb;
import pl.edu.agh.gg.domain.Vertex;
import pl.edu.agh.gg.domain.hyperEdge.HyperEdgeB;

import pl.edu.agh.gg.domain.hyperEdge.HyperEdgeI;
import pl.edu.agh.gg.domain.hyperEdge.HyperEdgeF;

import java.util.Arrays;

public class P6Test {
    private Vertex v1 = new Vertex(new Geom(0, 100), new Rgb(255, 200, 0), Vertex.Label.V);
    private Vertex v2 = new Vertex(new Geom(0, 50), new Rgb(255, 200, 0), Vertex.Label.V);
    private Vertex v3 = new Vertex(new Geom(50, 100), new Rgb(255, 200, 0), Vertex.Label.V);
    private Vertex v4 = new Vertex(new Geom(50, 0), new Rgb(255, 200, 0), Vertex.Label.V);
    private Vertex v5 = new Vertex(new Geom(150, 100), new Rgb(255, 200, 0), Vertex.Label.V);
    private Vertex v6 = new Vertex(new Geom(150, 0), new Rgb(255, 200, 0), Vertex.Label.V);

    private HyperEdgeI properSmallEdge = new HyperEdgeI(Arrays.asList(v1, v2, v3), true);
    private HyperEdgeI properBigEdge = new HyperEdgeI(Arrays.asList(v3, v4, v5, v6), false);

    private HyperEdgeF f1 = new HyperEdgeF(Arrays.asList(v3, v4));

    private P6 p6ToTest;
    private HyperGraph graph;

    @Before
    public void setUp() {
        graph = new HyperGraph();
        graph.add(v1);
        graph.add(v2);
        graph.add(v3);
        graph.add(v4);
        graph.add(v5);
        graph.add(v6);
        graph.add(f1);
    }

    @Test (expected = CannotApplyProductionException.class)
    public void WhenBigEdgeHasMoreThanOneCommonVerticesWithSmallEdge_ThrowError() {
        HyperEdgeI tooMuchCommonVerticesSmallEdge = new HyperEdgeI(Arrays.asList(v1, v2, v3, v4), true);
        graph.add(tooMuchCommonVerticesSmallEdge);
        graph.add(properBigEdge);
        p6ToTest = new P6(new FakeImage(), graph, tooMuchCommonVerticesSmallEdge);
        p6ToTest.apply(properBigEdge);
    }

    @Test (expected = CannotApplyProductionException.class)
    public void WhenBigEdgeHasWrongType_ThrowError() {
        HyperEdgeB b1 = new HyperEdgeB(Arrays.asList(v3, v4, v5, v6));
        graph.add(properSmallEdge);
        graph.add(b1);
        p6ToTest = new P6(new FakeImage(), graph, properSmallEdge);
        p6ToTest.apply(b1);
    }

    @Test (expected = CannotApplyProductionException.class)
    public void WhenSmallEdgeHasWrongNumberOfVertices_ThrowError() {
        HyperEdgeI wrongNumberOfVerticesSmallEdge = new HyperEdgeI(Arrays.asList(v1,  v3), true);
        graph.add(wrongNumberOfVerticesSmallEdge);
        graph.add(properBigEdge);
        p6ToTest = new P6(new FakeImage(), graph, wrongNumberOfVerticesSmallEdge);
        p6ToTest.apply(properBigEdge);
    }

    @Test (expected = CannotApplyProductionException.class)
    public void WhenSmallEdgeHasNotBreakSet_ThrowError() {
        HyperEdgeI breakNotSetSmallEdge = new HyperEdgeI(Arrays.asList(v1, v2, v3), false);
        graph.add(breakNotSetSmallEdge);
        graph.add(properBigEdge);
        p6ToTest = new P6(new FakeImage(), graph, breakNotSetSmallEdge);
        p6ToTest.apply(properBigEdge);
    }

    @Test
    public void WhenCanApplyP6_SetBreak() {
        p6ToTest = new P6(new FakeImage(), graph, properSmallEdge);
        graph.add(properBigEdge);
        graph.add(properSmallEdge);
        p6ToTest.apply(properBigEdge);
        Assert.assertTrue(properBigEdge.isBreak());
    }

}