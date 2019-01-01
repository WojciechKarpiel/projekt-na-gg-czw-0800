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

public class P5PropagationTest {
    @Test
    public void OneStepPropagationInRightDirection() {
        Vertex v1 = new Vertex(new Geom(0, 300), new Rgb(255, 200, 0), Vertex.Label.V);
        Vertex v2 = new Vertex(new Geom(0, 250), new Rgb(255, 200, 0), Vertex.Label.V);
        Vertex v3 = new Vertex(new Geom(50, 300), new Rgb(255, 200, 0), Vertex.Label.V);
        Vertex v4 = new Vertex(new Geom(50, 200), new Rgb(255, 200, 0), Vertex.Label.V);
        Vertex v5 = new Vertex(new Geom(150, 300), new Rgb(255, 200, 0), Vertex.Label.V);

        HyperEdgeI i1 = new HyperEdgeI(Arrays.asList(v1, v2, v3), false);
        HyperEdgeI i2 = new HyperEdgeI(Arrays.asList(v3, v4, v5), false);

        HyperEdgeF f1 = new HyperEdgeF(Arrays.asList(v3, v4));

        HyperGraph graph = new HyperGraph();
        graph.add(v1);
        graph.add(v2);
        graph.add(v3);
        graph.add(v4);
        graph.add(v5);

        graph.add(i1);
        graph.add(i2);

        graph.add(f1);

        P5 p5ToTest = new P5(new FakeImage(), graph);

        p5ToTest.apply(i1);
        Assert.assertTrue(i2.isBreak());
    }

    @Test
    public void OneStepPropagationInLeftDirection() {
        Vertex v1 = new Vertex(new Geom(300, 300), new Rgb(255, 200, 0), Vertex.Label.V);
        Vertex v2 = new Vertex(new Geom(300, 250), new Rgb(255, 200, 0), Vertex.Label.V);
        Vertex v3 = new Vertex(new Geom(250, 300), new Rgb(255, 200, 0), Vertex.Label.V);
        Vertex v4 = new Vertex(new Geom(250, 100), new Rgb(255, 200, 0), Vertex.Label.V);
        Vertex v5 = new Vertex(new Geom(150, 300), new Rgb(255, 200, 0), Vertex.Label.V);

        HyperEdgeI i1 = new HyperEdgeI(Arrays.asList(v1, v2, v3), false);
        HyperEdgeI i2 = new HyperEdgeI(Arrays.asList(v3, v4, v5), false);

        HyperEdgeF f1 = new HyperEdgeF(Arrays.asList(v3, v4));

        HyperGraph graph = new HyperGraph();
        graph.add(v1);
        graph.add(v2);
        graph.add(v3);
        graph.add(v4);
        graph.add(v5);

        graph.add(i1);
        graph.add(i2);

        graph.add(f1);

        P5 p5ToTest = new P5(new FakeImage(), graph);

        p5ToTest.apply(i1);
        Assert.assertTrue(i2.isBreak());
    }

    @Test
    public void OneStepPropagationInUpDirection() {
        Vertex v1 = new Vertex(new Geom(0, 0), new Rgb(255, 200, 0), Vertex.Label.V);
        Vertex v2 = new Vertex(new Geom(50, 0), new Rgb(255, 200, 0), Vertex.Label.V);
        Vertex v3 = new Vertex(new Geom(0, 50), new Rgb(255, 200, 0), Vertex.Label.V);
        Vertex v4 = new Vertex(new Geom(100, 50), new Rgb(255, 200, 0), Vertex.Label.V);
        Vertex v5 = new Vertex(new Geom(0, 150), new Rgb(255, 200, 0), Vertex.Label.V);

        HyperEdgeI i1 = new HyperEdgeI(Arrays.asList(v1, v2, v3), false);
        HyperEdgeI i2 = new HyperEdgeI(Arrays.asList(v3, v4, v5), false);

        HyperEdgeF f1 = new HyperEdgeF(Arrays.asList(v3, v4));

        HyperGraph graph = new HyperGraph();
        graph.add(v1);
        graph.add(v2);
        graph.add(v3);
        graph.add(v4);
        graph.add(v5);

        graph.add(i1);
        graph.add(i2);

        graph.add(f1);

        P5 p5ToTest = new P5(new FakeImage(), graph);

        p5ToTest.apply(i1);
        Assert.assertTrue(i2.isBreak());
    }


    @Test
    public void TwoStepPropagation() {
        Vertex v1 = new Vertex(new Geom(0, 300), new Rgb(255, 200, 0), Vertex.Label.V);
        Vertex v2 = new Vertex(new Geom(0, 250), new Rgb(255, 200, 0), Vertex.Label.V);
        Vertex v3 = new Vertex(new Geom(50, 300), new Rgb(255, 200, 0), Vertex.Label.V);
        Vertex v4 = new Vertex(new Geom(50, 200), new Rgb(255, 200, 0), Vertex.Label.V);
        Vertex v5 = new Vertex(new Geom(150, 300), new Rgb(255, 200, 0), Vertex.Label.V);
        Vertex v6 = new Vertex(new Geom(150, 50), new Rgb(255, 200, 0), Vertex.Label.V);
        Vertex v7 = new Vertex(new Geom(150, 300), new Rgb(255, 200, 0), Vertex.Label.V);

        HyperEdgeI i1 = new HyperEdgeI(Arrays.asList(v1, v2, v3), false);
        HyperEdgeI i2 = new HyperEdgeI(Arrays.asList(v3, v4, v5), false);
        HyperEdgeI i3 = new HyperEdgeI(Arrays.asList(v5, v6, v7), false);

        HyperEdgeF f1 = new HyperEdgeF(Arrays.asList(v3, v4));
        HyperEdgeF f2 = new HyperEdgeF(Arrays.asList(v5, v6));

        HyperGraph graph = new HyperGraph();
        graph.add(v1);
        graph.add(v2);
        graph.add(v3);
        graph.add(v4);
        graph.add(v5);
        graph.add(v6);
        graph.add(v7);

        graph.add(i1);
        graph.add(i2);
        graph.add(i3);

        graph.add(f1);
        graph.add(f2);

        P5 p5ToTest = new P5(new FakeImage(), graph);

        p5ToTest.apply(i1);
        Assert.assertTrue(i2.isBreak());
        Assert.assertTrue(i3.isBreak());
    }


}