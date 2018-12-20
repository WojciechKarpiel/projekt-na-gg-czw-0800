package pl.edu.agh.gg;

import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.io.*;
import pl.edu.agh.gg.domain.Vertex;
import pl.edu.agh.gg.domain.VertexLike;
import pl.edu.agh.gg.domain.hyperEdge.HyperEdge;
import pl.edu.agh.gg.domain.hyperEdge.HyperEdgeS;
import pl.edu.agh.gg.productions.P1;

import java.io.StringWriter;
import java.io.Writer;
import java.util.Collections;
import java.util.List;

public class Launcher {
    public static void main(String[] args) throws ExportException {
        HyperGraph graph = new HyperGraph();
        HyperEdge s = new HyperEdgeS(Collections.emptyList());
        graph.add(s);

        P1 p1 = new P1(new FakeImage(), graph);
        p1.apply(s);

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
