package pl.edu.agh.gg;

import org.jgrapht.Graph;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.io.*;
import pl.edu.agh.gg.domain.HaxEdge;
import pl.edu.agh.gg.domain.HyperEdge;
import pl.edu.agh.gg.domain.VertexLike;
import pl.edu.agh.gg.productions.P1;

import java.io.StringWriter;
import java.io.Writer;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Launcher {
    public static void main(String[] args) throws ExportException {
        SimpleGraph<VertexLike, HaxEdge> graph = new SimpleGraph<>(HaxEdge.class);

        HyperEdge s = new HyperEdge(HyperEdge.EdgeType.S);
        graph.addVertex(s);

        P1 p1 = new P1(new FakeImage(), graph);
        p1.apply(s);

        System.out.println("To można sobie narysować dot-em. (https://graphviz.org/) " +
                "Trzeba zapisać do pliku to co się wypluło i\n" +
                "neato -s8 -Tpng nazwa_pliku > obrazek.png\n" +
                "gdzie liczba po s to współczynnik skalowania (spróbuj ze zmienić na 3)\n" +
                "\n\n");
        GraphExporter<VertexLike, HaxEdge> exporter =
                new DOTExporter<>(VertexLike::getId, Object::toString, null, aleToOchydnexD(graph)
                        ,
                        null
                );

        Writer writer = new StringWriter();
        exporter.exportGraph(graph, writer);
        System.out.println(writer.toString());
    }

    private static ComponentAttributeProvider<VertexLike> aleToOchydnexD(Graph<VertexLike, HaxEdge> graph) {
        return component ->
                component.map(
                        v -> Collections.<String, Attribute>singletonMap("pos",
                                new DefaultAttribute<>(v.getPosition().getX() + "," + v.getPosition().getY() + "!", AttributeType.STRING)),
                        edge -> {
                            List<VertexLike> adjacent = graph.edgeSet().stream()
                                    .filter(h ->
                                            h.getSource().equals(edge) || h.getTarget().equals(edge))
                                    .map(h -> {
                                        if (h.getTarget().equals(edge)) {
                                            return h.getSource();
                                        } else {
                                            return h.getTarget();
                                        }
                                    })
                                    .collect(Collectors.toList());
                            double x = adjacent.stream().mapToInt(a -> a.getAsVertex().get().getPosition().getX())
                                    .average().getAsDouble();
                            double y = adjacent.stream().mapToInt(a -> a.getAsVertex().get().getPosition().getY())
                                    .average().getAsDouble();

                            return Collections.<String, Attribute>singletonMap("pos",
                                    new DefaultAttribute<>(x + "," + y + "!", AttributeType.STRING));
                        });
    }
}
