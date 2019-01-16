package pl.edu.agh.gg.P5Utils;

import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.io.*;
import pl.edu.agh.gg.HyperGraph;
import pl.edu.agh.gg.domain.Vertex;
import pl.edu.agh.gg.domain.VertexLike;

import java.io.*;
import java.util.Collections;
import java.util.List;

public class Drawer {
    public void draw(HyperGraph hyperGraph, String filename) throws IOException, ExportException {
        GraphExporter<VertexLike, DefaultEdge> exporter =
                new DOTExporter<>(VertexLike::getId, Object::toString, null, aleToOchydnexD(hyperGraph)
                        ,
                        null
                );

        BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
        exporter.exportGraph(hyperGraph, writer);
        writer.write(writer.toString());
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
