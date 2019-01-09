package pl.edu.agh.gg.productions;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import pl.edu.agh.gg.FakeImage;
import pl.edu.agh.gg.HyperGraph;
import pl.edu.agh.gg.domain.Geom;
import pl.edu.agh.gg.domain.Rgb;
import pl.edu.agh.gg.domain.Vertex;
import pl.edu.agh.gg.domain.hyperEdge.HyperEdgeB;
import pl.edu.agh.gg.domain.hyperEdge.HyperEdgeF;
import pl.edu.agh.gg.domain.hyperEdge.HyperEdgeI;

import java.util.ArrayList;
import java.util.Arrays;


public class PropagationTest {
    private ArrayList<Vertex> vertices = new ArrayList<>();
    private ArrayList<HyperEdgeI> iedges = new ArrayList<>();
    private ArrayList<HyperEdgeB> bedges = new ArrayList<>();
    private ArrayList<HyperEdgeF> fedges = new ArrayList<>();

    private P5 p5;
    private HyperGraph graph;

    @Before
    public void setUp() {
        for(int i = 0; i < 21; i++) {
            Vertex v = new Vertex(new Geom(0, 0), new Rgb(0, 0, 0), Vertex.Label.V);
            vertices.add(v);
        }

        HyperEdgeI h1 = new HyperEdgeI(Arrays.asList(vertices.get(1), vertices.get(5), vertices.get(6), vertices.get(7)), false);
        iedges.add(h1);
        HyperEdgeI h2 = new HyperEdgeI(Arrays.asList(vertices.get(5), vertices.get(12), vertices.get(9)), false);
        iedges.add(h2);
        HyperEdgeI h3 = new HyperEdgeI(Arrays.asList(vertices.get(12), vertices.get(2), vertices.get(13), vertices.get(9)), false);
        iedges.add(h3);
        HyperEdgeI h4 = new HyperEdgeI(Arrays.asList(vertices.get(9), vertices.get(14), vertices.get(7)), false);
        iedges.add(h4);
        HyperEdgeI h5 = new HyperEdgeI(Arrays.asList(vertices.get(9), vertices.get(13), vertices.get(8), vertices.get(14)), false);
        iedges.add(h5);
        HyperEdgeI h6 = new HyperEdgeI(Arrays.asList(vertices.get(6), vertices.get(11), vertices.get(20)), false);
        iedges.add(h6);
        HyperEdgeI h7 = new HyperEdgeI(Arrays.asList(vertices.get(7), vertices.get(15), vertices.get(11)), false);
        iedges.add(h7);
        HyperEdgeI h8 = new HyperEdgeI(Arrays.asList(vertices.get(7), vertices.get(14), vertices.get(10), vertices.get(15)), true);
        iedges.add(h8);
        HyperEdgeI h9 = new HyperEdgeI(Arrays.asList(vertices.get(14), vertices.get(8), vertices.get(16), vertices.get(10)), false);
        iedges.add(h9);
        HyperEdgeI h10 = new HyperEdgeI(Arrays.asList(vertices.get(20), vertices.get(11), vertices.get(17), vertices.get(4)), false);
        iedges.add(h10);
        HyperEdgeI h11 = new HyperEdgeI(Arrays.asList(vertices.get(15), vertices.get(15), vertices.get(18), vertices.get(17)), false);
        iedges.add(h11);
        HyperEdgeI h12 = new HyperEdgeI(Arrays.asList(vertices.get(15), vertices.get(10), vertices.get(19), vertices.get(18)), false);
        iedges.add(h12);
        HyperEdgeI h13 = new HyperEdgeI(Arrays.asList(vertices.get(10), vertices.get(16), vertices.get(3), vertices.get(19)), false);
        iedges.add(h13);

        HyperEdgeB b1 = new HyperEdgeB(Arrays.asList(vertices.get(1), vertices.get(5)));
        bedges.add(b1);
        HyperEdgeB b2 = new HyperEdgeB(Arrays.asList(vertices.get(5), vertices.get(12)));
        bedges.add(b2);
        HyperEdgeB b3 = new HyperEdgeB(Arrays.asList(vertices.get(12), vertices.get(2)));
        bedges.add(b3);
        HyperEdgeB b4 = new HyperEdgeB(Arrays.asList(vertices.get(2), vertices.get(13)));
        bedges.add(b4);
        HyperEdgeB b5 = new HyperEdgeB(Arrays.asList(vertices.get(13), vertices.get(8)));
        bedges.add(b5);
        HyperEdgeB b6 = new HyperEdgeB(Arrays.asList(vertices.get(8), vertices.get(16)));
        bedges.add(b6);
        HyperEdgeB b7 = new HyperEdgeB(Arrays.asList(vertices.get(16), vertices.get(3)));
        bedges.add(b7);
        HyperEdgeB b8 = new HyperEdgeB(Arrays.asList(vertices.get(3), vertices.get(19)));
        bedges.add(b8);
        HyperEdgeB b9 = new HyperEdgeB(Arrays.asList(vertices.get(19), vertices.get(18)));
        bedges.add(b9);
        HyperEdgeB b10 = new HyperEdgeB(Arrays.asList(vertices.get(18), vertices.get(17)));
        bedges.add(b10);
        HyperEdgeB b11 = new HyperEdgeB(Arrays.asList(vertices.get(17), vertices.get(4)));
        bedges.add(b11);
        HyperEdgeB b12 = new HyperEdgeB(Arrays.asList(vertices.get(4), vertices.get(20)));
        bedges.add(b12);
        HyperEdgeB b13 = new HyperEdgeB(Arrays.asList(vertices.get(20), vertices.get(6)));
        bedges.add(b13);
        HyperEdgeB b14 = new HyperEdgeB(Arrays.asList(vertices.get(6), vertices.get(1)));
        bedges.add(b14);

        HyperEdgeF f1 = new HyperEdgeF(Arrays.asList(vertices.get(12), vertices.get(9)));
        f1.setDirection(HyperEdgeF.Direction.UP);
        fedges.add(f1);
        HyperEdgeF f2 = new HyperEdgeF(Arrays.asList(vertices.get(9), vertices.get(13)));
        f2.setDirection(HyperEdgeF.Direction.RIGHT);
        fedges.add(f2);
        HyperEdgeF f3 = new HyperEdgeF(Arrays.asList(vertices.get(9), vertices.get(14)));
        f3.setDirection(HyperEdgeF.Direction.BOTTOM);
        fedges.add(f3);
        HyperEdgeF f4 = new HyperEdgeF(Arrays.asList(vertices.get(9)));
        f4.setDirection(HyperEdgeF.Direction.LEFT);
        fedges.add(f4);
        HyperEdgeF f5 = new HyperEdgeF(Arrays.asList(vertices.get(14), vertices.get(10)));
        f5.setDirection(HyperEdgeF.Direction.UP);
        fedges.add(f5);
        HyperEdgeF f6 = new HyperEdgeF(Arrays.asList(vertices.get(10), vertices.get(16)));
        f6.setDirection(HyperEdgeF.Direction.RIGHT);
        fedges.add(f6);
        HyperEdgeF f7 = new HyperEdgeF(Arrays.asList(vertices.get(10), vertices.get(19)));
        f7.setDirection(HyperEdgeF.Direction.BOTTOM);
        fedges.add(f7);
        HyperEdgeF f8 = new HyperEdgeF(Arrays.asList(vertices.get(15), vertices.get(10)));
        f8.setDirection(HyperEdgeF.Direction.LEFT);
        fedges.add(f8);
        HyperEdgeF f9 = new HyperEdgeF(Arrays.asList(vertices.get(11)));
        f9.setDirection(HyperEdgeF.Direction.UP);
        fedges.add(f9);
        HyperEdgeF f10 = new HyperEdgeF(Arrays.asList(vertices.get(11), vertices.get(15)));
        f10.setDirection(HyperEdgeF.Direction.RIGHT);
        fedges.add(f10);
        HyperEdgeF f11 = new HyperEdgeF(Arrays.asList(vertices.get(11), vertices.get(17)));
        f11.setDirection(HyperEdgeF.Direction.BOTTOM);
        fedges.add(f11);
        HyperEdgeF f12 = new HyperEdgeF(Arrays.asList(vertices.get(20), vertices.get(11)));
        f12.setDirection(HyperEdgeF.Direction.LEFT);
        fedges.add(f12);
        HyperEdgeF f13 = new HyperEdgeF(Arrays.asList(vertices.get(7), vertices.get(14)));
        f13.setDirection(HyperEdgeF.Direction.LEFT);
        fedges.add(f13);
        HyperEdgeF f14 = new HyperEdgeF(Arrays.asList(vertices.get(14), vertices.get(8)));
        f14.setDirection(HyperEdgeF.Direction.RIGHT);
        fedges.add(f14);
        HyperEdgeF f15 = new HyperEdgeF(Arrays.asList(vertices.get(7), vertices.get(15)));
        f15.setDirection(HyperEdgeF.Direction.UP);
        fedges.add(f15);
        HyperEdgeF f16 = new HyperEdgeF(Arrays.asList(vertices.get(15), vertices.get(18)));
        f16.setDirection(HyperEdgeF.Direction.BOTTOM);
        fedges.add(f16);


        graph = new HyperGraph();

        for(int i = 1; i < vertices.size(); i++) {
            graph.add(vertices.get(i));
        }

        for(int i = 0; i < iedges.size(); i++) {
            graph.add(iedges.get(i));
        }

        for(int i = 0; i < bedges.size(); i++) {
            graph.add(bedges.get(i));
        }

        for(int i = 0; i < fedges.size(); i++) {
            graph.add(fedges.get(i));
        }

        p5 = new P5(new FakeImage(), graph);
    }

    @Test
    @Ignore
    public void Point10() {
        p5.apply(iedges.get(7));
        Assert.assertTrue(iedges.get(0).isBreak());
    }



}