package pl.edu.agh.gg.productions;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pl.edu.agh.gg.FakeImage;
import pl.edu.agh.gg.HyperGraph;
import pl.edu.agh.gg.domain.VertexLike;
import pl.edu.agh.gg.domain.hyperEdge.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class P1Test {
    private HyperEdgeS hyperEdgeS = new HyperEdgeS(Collections.emptyList());
    private HyperEdgeB hyperEdgeB = new HyperEdgeB(Collections.emptyList());
    private HyperEdgeI hyperEdgeI = new HyperEdgeI(Collections.emptyList(), false);
    private HyperEdgeF hyperEdgeF = new HyperEdgeF(Collections.emptyList());

    private P1 p1Test;
    private HyperGraph graph;

    @Before
    public void setUp() {
        graph = new HyperGraph();

        graph.add(hyperEdgeB);
        graph.add(hyperEdgeF);
        graph.add(hyperEdgeI);
        graph.add(hyperEdgeS);

        p1Test = new P1(new FakeImage(), graph);
    }

    @Test(expected = CannotApplyProductionException.class)
    public void whenEdgeIsLabeledB_ThrowException() {
        p1Test.apply(hyperEdgeB);
    }

    @Test(expected = CannotApplyProductionException.class)
    public void whenEdgeIsLabeledI_ThrowException() {
        p1Test.apply(hyperEdgeI);
    }

    @Test(expected = CannotApplyProductionException.class)
    public void whenEdgeIsLabeledF_ThrowException() {
        p1Test.apply(hyperEdgeF);
    }

    @Test
    public void afterProductionGraphShouldNotContainFirstEdge() {
        graph = new HyperGraph();
        graph.add(hyperEdgeS);
        p1Test = new P1(new FakeImage(), graph);

        p1Test.apply(hyperEdgeS);

        List<HyperEdge> hyperEdges = graph
                .vertexSet()
                .stream()
                .filter(VertexLike::isEdge)
                .map(e -> e.getAsEdge().get())
                .collect(Collectors.toList());

        Assert.assertFalse(hyperEdges.contains(hyperEdgeS));
    }

    @Test
    public void afterProductionGraphShouldContainEdgesWithLabelF() {
        graph = new HyperGraph();
        graph.add(hyperEdgeS);
        p1Test = new P1(new FakeImage(), graph);

        p1Test.apply(hyperEdgeS);

        List<HyperEdge> hyperEdgeFList = graph
                .vertexSet()
                .stream()
                .filter(VertexLike::isEdge)
                .map(e -> e.getAsEdge().get())
                .filter(edge -> HyperEdge.EdgeLabel.B.equals(edge.getEdgeLabel()))
                .collect(Collectors.toList());

        Assert.assertEquals(hyperEdgeFList.size(), 4);
    }

    @Test
    public void afterProductionGraphShouldContainForEdgesWithLabelI() {
        graph = new HyperGraph();
        graph.add(hyperEdgeS);
        p1Test = new P1(new FakeImage(), graph);

        p1Test.apply(hyperEdgeS);

        List<HyperEdge> hyperEdgeIList = graph
                .vertexSet()
                .stream()
                .filter(VertexLike::isEdge)
                .map(e -> e.getAsEdge().get())
                .filter(edge -> HyperEdge.EdgeLabel.I.equals(edge.getEdgeLabel()))
                .collect(Collectors.toList());

        Assert.assertEquals(hyperEdgeIList.size(), 1);
        Assert.assertEquals(hyperEdgeIList.get(0).getClass(), HyperEdgeI.class);
        Assert.assertFalse(((HyperEdgeI) hyperEdgeIList.get(0)).isBreak());
    }
}
