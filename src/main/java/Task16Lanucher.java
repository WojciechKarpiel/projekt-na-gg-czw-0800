import org.jgrapht.io.ExportException;
import pl.edu.agh.gg.HyperGraph;
import pl.edu.agh.gg.P5Utils.GraphDrawer;
import pl.edu.agh.gg.adaptation.Task16;
import pl.edu.agh.gg.domain.VertexLike;
import pl.edu.agh.gg.domain.hyperEdge.HyperEdge;
import pl.edu.agh.gg.domain.hyperEdge.HyperEdgeF;
import pl.edu.agh.gg.domain.hyperEdge.HyperEdgeI;
import pl.edu.agh.gg.domain.hyperEdge.HyperEdgeS;
import pl.edu.agh.gg.productions.P1;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Task16Lanucher {
    public static void main(String[] args) throws Exception {
        File bmpFile = new File("bitMap.png");
        BufferedImage image = ImageIO.read(bmpFile);
        HyperEdgeS hyperEdgeS = new HyperEdgeS(Collections.emptyList());

        HyperGraph graph = new HyperGraph();
        graph.add(hyperEdgeS);
        P1 p1 = new P1(image, graph);

        p1.apply(hyperEdgeS);
        List<HyperEdgeI> iEdges = graph.vertexSet().stream()
                .filter(VertexLike::isEdge)
                .map(v -> v.getAsEdge().get())
                .filter(e -> e.getEdgeLabel() == HyperEdge.EdgeLabel.I)
                .map(e -> (HyperEdgeI) e)
                .collect(Collectors.toList());
        Task16 t16 = new Task16(iEdges);
        GraphDrawer graphDrawer = new GraphDrawer();
        graphDrawer.draw(graph, "Before.dot");
        t16.markHyperedgesForAdaptation(graph, 0.01, 1000);
        graphDrawer.draw(graph, "After.dot");

    }
}
