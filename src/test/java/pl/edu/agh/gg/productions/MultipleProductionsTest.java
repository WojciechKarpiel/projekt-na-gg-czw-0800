package pl.edu.agh.gg.productions;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.io.DOTExporter;
import org.jgrapht.io.ExportException;
import org.jgrapht.io.GraphExporter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pl.edu.agh.gg.FakeImage;
import pl.edu.agh.gg.HyperGraph;
import pl.edu.agh.gg.Launcher;
import pl.edu.agh.gg.domain.Vertex;
import pl.edu.agh.gg.domain.VertexLike;
import pl.edu.agh.gg.domain.hyperEdge.*;

import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


public class MultipleProductionsTest {
    private ArrayList<Vertex> vertices = new ArrayList<>();
    private ArrayList<HyperEdgeI> iedges = new ArrayList<>();
    private ArrayList<HyperEdgeB> bedges = new ArrayList<>();
    private ArrayList<HyperEdgeF> fedges = new ArrayList<>();


    private P1 p1;
    private P2 p2;
    private P3 p3;
    private P4 p4;
    private P5 p5;


    private HyperGraph graph;

    @Before
    public void setUp() {
        graph = new HyperGraph();
        p1 = new P1(new FakeImage(), graph);
        p2 = new P2(new FakeImage(), graph);
        p3 = new P3(new FakeImage(), graph);
        p4 = new P4(new FakeImage(), graph);
        p5 = new P5(new FakeImage(), graph);


        HyperEdge s = new HyperEdgeS(Collections.emptyList());
        graph.add(s);

        //produkcja p1
        p1.apply(s);

        List<HyperEdgeI> listOfIEdges = getListOfIEdges(graph);

        //produkcja p5
        p5.apply(listOfIEdges.get(0));

        //produkcja p2
        p2.apply(listOfIEdges.get(0));

        List<HyperEdgeB> listOfBEdges = getListOfBEdges(graph);

        //cztery produkcje p3
        listOfBEdges.forEach(e -> p3.apply(e));

        //znajdz sasiadow wierzcholka w lewym gornym rogu (dla nich nie wykonujemy produkcji)
        Set<VertexLike> neighborsOfTopLeft = Graphs.neighborSetOf(graph, getTopLeftVertex(graph));

        //trzy produkcje p2
        getListOfIEdges(graph).stream().filter(e -> !neighborsOfTopLeft.contains(e)).forEach(e -> p5.apply(e));

        //lista pierwszych czterech krawedzi F
        List<HyperEdgeF> listOfOriginalFEdges = getListOfFEdges(graph);

        //trzy produkcje p2
        getListOfIEdges(graph).stream().filter(e -> !neighborsOfTopLeft.contains(e)).forEach(e -> p2.apply(e));

        //szesc produkcji p3
        getListOfBEdges(graph).stream().filter(e -> !neighborsOfTopLeft.contains(e)).forEach(e -> p3.apply(e));

        GraphExporter<VertexLike, DefaultEdge> exporter =
                new DOTExporter<>(VertexLike::getId, Object::toString, null, Launcher.aleToOchydnexD(graph)
                        ,
                        null
                );

        //dwie produkcje p4

        listOfOriginalFEdges.stream().filter(e -> e.getDirection() == HyperEdgeF.Direction.BOTTOM || e.getDirection() == HyperEdgeF.Direction.RIGHT).forEach(e -> p4.apply(e));
    }

    //18 before p4
    @Test
    public void TestNumberOfVertices() {
        Assert.assertEquals(20, graph.vertexSet().stream().filter(VertexLike::isVertex).map(e -> e.getAsVertex().get()).count());
    }

    @Test
    public void TestNumberOfIEdges() {
        Assert.assertEquals(13, getListOfIEdges(graph).size());
    }

    @Test
    public void TestNumberOfBEdges() {
        Assert.assertEquals(14, getListOfBEdges(graph).size());
    }

    //16 before p4
    @Test
    public void TestNumberOfFEdges() {
        Assert.assertEquals(18, getListOfFEdges(graph).size());
    }

    public static List<HyperEdgeI> getListOfIEdges(HyperGraph graph) {
        HyperEdge.EdgeLabel label = HyperEdge.EdgeLabel.I;

        List<HyperEdgeI> edges = graph.vertexSet().stream()
                .filter(VertexLike::isEdge)
                .map(v -> v.getAsEdge().get())
                .filter(e -> e.getEdgeLabel() == label)
                .map(e -> (HyperEdgeI) e)
                .collect(Collectors.toList());
        return edges;
    }

    public static List<HyperEdgeB> getListOfBEdges(HyperGraph graph) {
        HyperEdge.EdgeLabel label = HyperEdge.EdgeLabel.B;

        List<HyperEdgeB> edges = graph.vertexSet().stream()
                .filter(VertexLike::isEdge)
                .map(v -> v.getAsEdge().get())
                .filter(e -> e.getEdgeLabel() == label)
                .map(e -> (HyperEdgeB) e)
                .collect(Collectors.toList());
        return edges;
    }

    public static List<HyperEdgeF> getListOfFEdges(HyperGraph graph) {
        HyperEdge.EdgeLabel label = HyperEdge.EdgeLabel.F;

        List<HyperEdgeF> edges = graph.vertexSet().stream()
                .filter(VertexLike::isEdge)
                .map(v -> v.getAsEdge().get())
                .filter(e -> e.getEdgeLabel() == label)
                .map(e -> (HyperEdgeF) e)
                .collect(Collectors.toList());
        return edges;
    }

    public static List<HyperEdgeS> getListOfSEdges(HyperGraph graph) {
        HyperEdge.EdgeLabel label = HyperEdge.EdgeLabel.S;

        List<HyperEdgeS> edges = graph.vertexSet().stream()
                .filter(VertexLike::isEdge)
                .map(v -> v.getAsEdge().get())
                .filter(e -> e.getEdgeLabel() == label)
                .map(e -> (HyperEdgeS) e)
                .collect(Collectors.toList());
        return edges;
    }

    public static Vertex getTopLeftVertex(HyperGraph graph) {

        Vertex edges = graph.vertexSet().stream()
                .filter(VertexLike::isVertex)
                .map(v -> v.getAsVertex().get())
                .min((v1, v2) -> Integer.compare(v1.getGeom().getX() + v1.getGeom().getY()/2, v2.getGeom().getX()  + v2.getGeom().getY())/2).get();
        return edges;
    }


}