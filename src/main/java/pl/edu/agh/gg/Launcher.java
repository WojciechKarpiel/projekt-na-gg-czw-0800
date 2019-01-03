package pl.edu.agh.gg;

import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.io.*;
import pl.edu.agh.gg.domain.Geom;
import pl.edu.agh.gg.domain.Rgb;
import pl.edu.agh.gg.domain.Vertex;
import pl.edu.agh.gg.domain.VertexLike;
import pl.edu.agh.gg.domain.hyperEdge.*;
import pl.edu.agh.gg.productions.P1;
import pl.edu.agh.gg.productions.P3;

import java.io.StringWriter;
import java.io.Writer;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Launcher {
    public static void main(String[] args) throws ExportException {
        HyperGraph graph = new HyperGraph();
//        HyperEdge s = new HyperEdgeS(Collections.emptyList());
//        graph.add(s);
//
//        P1 p1 = new P1(new FakeImage(), graph);
//        p1.apply(s);
        // That code can be used to create test for P3
        FakeImage im = new FakeImage();
        Vertex center = new Vertex(new Geom(50, 40), new Rgb(255, 200, 0), Vertex.Label.V);
        Vertex v1 = new Vertex(new Geom(0, 80), new Rgb(255, 200, 0), Vertex.Label.V);
        Vertex v2 = new Vertex(new Geom(100, 80), new Rgb(255, 200, 0), Vertex.Label.V);
        Vertex v3 = new Vertex(new Geom(0, 0), new Rgb(255, 200, 0), Vertex.Label.V);
        HyperEdgeB b1 = new HyperEdgeB(Arrays.asList(v1, v2));
        HyperEdgeB b2 = new HyperEdgeB(Arrays.asList(v1, v3));
        HyperEdgeF f1 = new HyperEdgeF(Arrays.asList(center));
        f1.setDirection(HyperEdgeF.Direction.UP);
        HyperEdgeF f2 = new HyperEdgeF(Arrays.asList(center));
        f2.setDirection(HyperEdgeF.Direction.LEFT);
        HyperEdgeI i1 = new HyperEdgeI(Arrays.asList(v1, center), false);
        HyperEdgeI i2 = new HyperEdgeI(Arrays.asList(v2, center), false);
        HyperEdgeI i3 = new HyperEdgeI(Arrays.asList(v3, center), false);
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
        P3 p = new P3(im, graph);
        p.apply(b1);
        p.apply(b2);
        System.out.println("To można sobie narysować dot-em. (https://graphviz.org/) " +
                "Trzeba zapisać do pliku to co się wypluło i\n" +
                "neato -s8 -Tpng nazwa_pliku > obrazek.png\n" +
                "gdzie liczba po s to współczynnik skalowania (spróbuj ze zmienić na 3)\n" +
                "\n\n");
        GraphExporter<VertexLike, DefaultEdge> exporter =
                new DOTExporter<>(VertexLike::getId, Object::toString, null, aleToOchydnexD(graph)
                        ,
                        null
                );

        Writer writer = new StringWriter();
        exporter.exportGraph(graph, writer);
        System.out.println(writer.toString());
    }

    private static ComponentAttributeProvider<VertexLike> aleToOchydnexD(HyperGraph graph) {
        return component ->
                component.map(
                        v -> Collections.<String, Attribute>singletonMap("pos",
                                new DefaultAttribute<>(v.getGeom().getX() + "," + v.getGeom().getY() + "!", AttributeType.STRING)),
                        edge -> {
                            List<Vertex> adjacent = edge.getConnectedVertices();
                            double x = adjacent.stream().mapToInt(a -> a.getAsVertex().get().getGeom().getX())
                                    .average().getAsDouble();
                            double y = adjacent.stream().mapToInt(a -> a.getAsVertex().get().getGeom().getY())
                                    .average().getAsDouble();

                            return Collections.<String, Attribute>singletonMap("pos",
                                    new DefaultAttribute<>(x + "," + y + "!", AttributeType.STRING));
                        });
    }
}
