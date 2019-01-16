package pl.edu.agh.gg.P5Utils;

import pl.edu.agh.gg.FakeImage;
import pl.edu.agh.gg.HyperGraph;
import pl.edu.agh.gg.domain.Geom;
import pl.edu.agh.gg.domain.Rgb;
import pl.edu.agh.gg.domain.Vertex;
import pl.edu.agh.gg.domain.hyperEdge.HyperEdgeF;
import pl.edu.agh.gg.domain.hyperEdge.HyperEdgeI;
import pl.edu.agh.gg.productions.P5;

import java.util.Arrays;

public class LauncherP5P6 {
    private static GraphDrawer graphDrawer = new GraphDrawer();

    public static void main(String[] args) throws Exception {
        visualiseP5();
        visualisePropagation();
    }

    public static void visualiseP5() throws Exception {
        Vertex v1 = new Vertex(new Geom(0, 0), new Rgb(255, 200, 0), Vertex.Label.V);
        Vertex v2 = new Vertex(new Geom(0, 100), new Rgb(255, 200, 0), Vertex.Label.V);
        Vertex v3 = new Vertex(new Geom(100, 0), new Rgb(255, 200, 0), Vertex.Label.V);
        Vertex v4 = new Vertex(new Geom(100, 100), new Rgb(255, 200, 0), Vertex.Label.V);
        FakeImage im = new FakeImage();
        HyperEdgeI i2 = new HyperEdgeI(Arrays.asList(v1, v2, v3, v4), false);
        HyperGraph graph = new HyperGraph();
        graph.add(v1);
        graph.add(v2);
        graph.add(v3);
        graph.add(i2);
        graphDrawer.draw(graph, "P5Before.dot");
        P5 p5 = new P5(im, graph);
        p5.apply(i2);
        graphDrawer.draw(graph, "P5After.dot");
    }

    public static void visualisePropagation() throws Exception {
        Vertex v1 = new Vertex(new Geom(0, 300), new Rgb(255, 200, 0), Vertex.Label.V);
        Vertex v2 = new Vertex(new Geom(0, 250), new Rgb(255, 200, 0), Vertex.Label.V);
        Vertex v3 = new Vertex(new Geom(50, 300), new Rgb(255, 200, 0), Vertex.Label.V);
        Vertex v4 = new Vertex(new Geom(50, 200), new Rgb(255, 200, 0), Vertex.Label.V);
        Vertex v5 = new Vertex(new Geom(150, 300), new Rgb(255, 200, 0), Vertex.Label.V);
        Vertex v6 = new Vertex(new Geom(150, 50), new Rgb(255, 200, 0), Vertex.Label.V);
        Vertex v7 = new Vertex(new Geom(150, 300), new Rgb(255, 200, 0), Vertex.Label.V);

        HyperEdgeI properSmallEdge = new HyperEdgeI(Arrays.asList(v1, v2, v3), false);
        HyperEdgeI properBigEdge = new HyperEdgeI(Arrays.asList(v3, v4, v5), false);
        HyperEdgeI secondBigEdge = new HyperEdgeI(Arrays.asList(v5, v6, v7), false);

        HyperEdgeF properFaceEdge = new HyperEdgeF(Arrays.asList(v3, v4));
        HyperEdgeF secondFaceEdge = new HyperEdgeF(Arrays.asList(v5, v6));

        FakeImage im = new FakeImage();

        HyperGraph graph = new HyperGraph();
        graph.add(v1);
        graph.add(v2);
        graph.add(v3);
        graph.add(v4);
        graph.add(v5);
        graph.add(v6);
        graph.add(v7);

        graph.add(properSmallEdge);
        graph.add(properBigEdge);
        graph.add(properFaceEdge);
        graph.add(secondBigEdge);
        graph.add(secondFaceEdge);

        graphDrawer.draw(graph, "PropagationBefore.dot");
        P5 p5 = new P5(im, graph);
        p5.apply(properSmallEdge);
        graphDrawer.draw(graph, "PropagationAfter.dot");
    }
}

