package pl.edu.agh.gg;

import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.io.DOTExporter;
import org.jgrapht.io.ExportException;
import org.jgrapht.io.GraphExporter;
import pl.edu.agh.gg.domain.*;

import java.io.StringWriter;
import java.io.Writer;

public class Launcher {
    public static void main(String[] args) throws ExportException {
        SimpleGraph<VertexLike, DefaultEdge> graph = new SimpleGraph<>(DefaultEdge.class);

        Vertex v1 = new Vertex(new Position(0, 0), new Rgb(1, 2, 3));
        Vertex v2 = new Vertex(new Position(0, 100), new Rgb(13, 22, 34));
        Vertex v3 = new Vertex(new Position(100, 0), new Rgb(123, 212, 14));
        Vertex v4 = new Vertex(new Position(100, 100), new Rgb(123, 112, 114));
        HyperEdge b1 = new HyperEdge(HyperEdge.EdgeType.B);
        HyperEdge b2 = new HyperEdge(HyperEdge.EdgeType.B);
        HyperEdge b3 = new HyperEdge(HyperEdge.EdgeType.B);
        HyperEdge b4 = new HyperEdge(HyperEdge.EdgeType.B);
        HyperEdge i = new HyperEdge(HyperEdge.EdgeType.I_NOBREAK);

        graph.addVertex(v1);
        graph.addVertex(v2);
        graph.addVertex(v3);
        graph.addVertex(v4);
        graph.addVertex(i);
        graph.addVertex(b1);
        graph.addVertex(b2);
        graph.addVertex(b3);
        graph.addVertex(b4);

        // jedyne prawile "krawędzie" to Vertex-HyperEdge i HyperEdge-Vertex
        graph.addEdge(v1, i);
        graph.addEdge(v2, i);
        graph.addEdge(v3, i);
        graph.addEdge(v4, i);

        graph.addEdge(v1, b1);
        graph.addEdge(v2, b1);
        graph.addEdge(v2, b2);
        graph.addEdge(v3, b2);
        graph.addEdge(v3, b3);
        graph.addEdge(v4, b3);
        graph.addEdge(v4, b4);
        graph.addEdge(v1, b4);


        System.out.println("To można sobie narysować dot-em. (https://graphviz.org/) " +
                "Trzeba zapisać do pliku to co się wypluło i\n" +
                "dot -Tpng nazwa_pliku > obrazek.png\n" +
                "Jeśli ktoś umie zrobić zeby wyglądało lepiej to niech powie\n" +
                "To co próbuję tu zrobić to: wyjście produkcji P1" +
                "\n\n");
        GraphExporter<VertexLike, DefaultEdge> exporter =
                new DOTExporter<>(VertexLike::getId, Object::toString, null);
        Writer writer = new StringWriter();
        exporter.exportGraph(graph, writer);
        System.out.println(writer.toString());
    }
}
