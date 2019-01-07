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

import java.util.Arrays;


public class P5Test {
    private Vertex v1 = new Vertex(new Geom(0, 0), new Rgb(255, 200, 0), Vertex.Label.V);
    private Vertex v2 = new Vertex(new Geom(0, 100), new Rgb(255, 200, 0), Vertex.Label.V);
    private Vertex v3 = new Vertex(new Geom(100, 0), new Rgb(255, 200, 0), Vertex.Label.V);
    private Vertex v4 = new Vertex(new Geom(100, 100), new Rgb(255, 200, 0), Vertex.Label.V);

    private HyperEdgeB b1 = new HyperEdgeB(Arrays.asList(v1, v2, v3, v4));

    private HyperEdgeI i1 = new HyperEdgeI(Arrays.asList(v1, v2, v3, v4), true);
    private HyperEdgeI i2 = new HyperEdgeI(Arrays.asList(v1, v2, v3, v4), false);
    private HyperEdgeI i3 = new HyperEdgeI(Arrays.asList(v1, v2, v3), false);


    private P5 p5ToTest;

    @Before
    public void setUp() {
        HyperGraph graph = new HyperGraph();
        graph.add(v1);
        graph.add(v2);
        graph.add(v3);
        graph.add(b1);
        graph.add(i1);
        graph.add(i2);
        graph.add(i3);
        p5ToTest = new P5(new FakeImage(), graph);
    }


    @Test(expected = CannotApplyProductionException.class)
    public void WhenNotHyperEdge_ThrowException() {
        p5ToTest.apply(v1);
    }

    @Test(expected = CannotApplyProductionException.class)
    public void WhenWrongEdgeType_ThrowException() {
        p5ToTest.apply(b1);
    }

    @Test(expected = CannotApplyProductionException.class)
    public void WhenEdgeHasBreakSet_ThrowException() {
        p5ToTest.apply(i1);
    }

    @Test
    public void WhenCenterEdgeHasBreakNotSet_SetBreak() {
        p5ToTest.apply(i2);
        Assert.assertTrue(i2.isBreak());
    }

}