package pl.edu.agh.gg.productions;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pl.edu.agh.gg.FakeImage;
import pl.edu.agh.gg.HyperGraph;
import pl.edu.agh.gg.domain.Geom;
import pl.edu.agh.gg.domain.Rgb;
import pl.edu.agh.gg.domain.Vertex;
import pl.edu.agh.gg.domain.hyperEdge.HyperEdgeF;
import pl.edu.agh.gg.domain.hyperEdge.HyperEdgeI;

import java.util.Arrays;
import java.util.Collections;


public class P5PropagationTest {
    private Vertex v1 = new Vertex(new Geom(0, 300), new Rgb(255, 200, 0), Vertex.Label.V);
    private Vertex v2 = new Vertex(new Geom(0, 250), new Rgb(255, 200, 0), Vertex.Label.V);
    private Vertex v3 = new Vertex(new Geom(50, 300), new Rgb(255, 200, 0), Vertex.Label.V);
    private Vertex v4 = new Vertex(new Geom(50, 200), new Rgb(255, 200, 0), Vertex.Label.V);
    private Vertex v5 = new Vertex(new Geom(150, 300), new Rgb(255, 200, 0), Vertex.Label.V);

    private HyperEdgeI properSmallEdge = new HyperEdgeI(Arrays.asList(v1, v2, v3), false);
    private HyperEdgeI properBigEdge = new HyperEdgeI(Arrays.asList(v3, v4, v5), false);
    private HyperEdgeF properFaceEdge = new HyperEdgeF(Arrays.asList(v3, v4));

    private P5 p5;
    private HyperGraph graph;

    @Before
    public void setUp() {
        graph = new HyperGraph();
        graph.add(v1);
        graph.add(v2);
        graph.add(v3);
        graph.add(v4);
        graph.add(v5);
        graph.add(properSmallEdge);
        graph.add(properBigEdge);
        graph.add(properFaceEdge);
        p5 = new P5(new FakeImage(), graph);
    }

    @Test
    public void WhenSmallEdgeHasWrongNumberOfVertices_NotPropagate() {
        Vertex v6 = new Vertex(new Geom(50, 250), new Rgb(255, 200, 0), Vertex.Label.V);
        HyperEdgeI tooLittleVertices = new HyperEdgeI(Collections.singletonList(v3), false);
        HyperEdgeI tooMuchVertices = new HyperEdgeI(Arrays.asList(v1, v2, v3, v6), false);
        graph.add(v6);
        graph.add(tooLittleVertices);
        graph.add(tooMuchVertices);

        p5.apply(tooMuchVertices);
        p5.apply(tooLittleVertices);
        Assert.assertFalse(properBigEdge.isBreak());
    }

    @Test
    public void WhenBorderDiagonally_NotPropagate() {
        Vertex v6 = new Vertex(new Geom(50, 400), new Rgb(255, 200, 0), Vertex.Label.V);
        Vertex v7 = new Vertex(new Geom(150, 400), new Rgb(255, 200, 0), Vertex.Label.V);
        HyperEdgeI bigEdge = new HyperEdgeI(Arrays.asList(v3, v6, v7), false);
        HyperEdgeF f1 = new HyperEdgeF(Arrays.asList(v3, v6));
        graph.add(v6);
        graph.add(v7);
        graph.add(bigEdge);
        graph.add(f1);

        p5.apply(properSmallEdge);
        Assert.assertFalse(bigEdge.isBreak());
    }


    @Test
    public void WhenNoFaceEdgePresent_NotPropagate() {
        graph.removeVertex(properFaceEdge.getAsEdge().get());

        p5.apply(properSmallEdge);
        Assert.assertFalse(properBigEdge.isBreak());
    }


    @Test
    public void OneStepPropagationInRightDirection() {
        p5.apply(properSmallEdge);
        Assert.assertTrue(properBigEdge.isBreak());
    }

    @Test
    public void OneStepPropagationInLeftDirection() {
        Vertex v6 = new Vertex(new Geom(-50, 300), new Rgb(255, 200, 0), Vertex.Label.V);
        Vertex v7 = new Vertex(new Geom(-50, 200), new Rgb(255, 200, 0), Vertex.Label.V);
        Vertex v8 = new Vertex(new Geom(-150, 300), new Rgb(255, 200, 0), Vertex.Label.V);
        HyperEdgeI smallEdge = new HyperEdgeI(Arrays.asList(v1, v2, v6), false);
        HyperEdgeI bigEdge = new HyperEdgeI(Arrays.asList(v6, v7, v8), false);
        HyperEdgeF faceEdge = new HyperEdgeF(Arrays.asList(v6, v7));
        graph.add(v6);
        graph.add(v7);
        graph.add(v8);
        graph.add(smallEdge);
        graph.add(bigEdge);
        graph.add(faceEdge);

        p5.apply(smallEdge);
        Assert.assertTrue(bigEdge.isBreak());
    }

    @Test
    public void OneStepPropagationInUpDirection() {
        Vertex v6 = new Vertex(new Geom(50, 250), new Rgb(255, 200, 0), Vertex.Label.V);
        Vertex v7 = new Vertex(new Geom(100, 300), new Rgb(255, 200, 0), Vertex.Label.V);
        Vertex v8 = new Vertex(new Geom(0, 400), new Rgb(255, 200, 0), Vertex.Label.V);
        HyperEdgeI smallEdge = new HyperEdgeI(Arrays.asList(v1, v2, v6), false);
        HyperEdgeI bigEdge = new HyperEdgeI(Arrays.asList(v1, v7, v8), false);
        HyperEdgeF faceEdge = new HyperEdgeF(Arrays.asList(v1, v7));
        graph.add(v6);
        graph.add(v7);
        graph.add(v8);
        graph.add(smallEdge);
        graph.add(bigEdge);
        graph.add(faceEdge);

        p5.apply(smallEdge);
        Assert.assertTrue(bigEdge.isBreak());
    }


    @Test
    public void TwoStepPropagation() {
        Vertex v6 = new Vertex(new Geom(150, 50), new Rgb(255, 200, 0), Vertex.Label.V);
        Vertex v7 = new Vertex(new Geom(150, 300), new Rgb(255, 200, 0), Vertex.Label.V);
        HyperEdgeI secondBigEdge = new HyperEdgeI(Arrays.asList(v5, v6, v7), false);
        HyperEdgeF secondFaceEdge = new HyperEdgeF(Arrays.asList(v5, v6));
        graph.add(v6);
        graph.add(v7);
        graph.add(secondBigEdge);
        graph.add(secondFaceEdge);

        p5.apply(properSmallEdge);
        Assert.assertTrue(properBigEdge.isBreak());
        Assert.assertTrue(secondBigEdge.isBreak());
    }


}