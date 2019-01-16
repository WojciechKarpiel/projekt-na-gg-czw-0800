package pl.edu.agh.gg.approximation;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pl.edu.agh.gg.FakeImage;
import pl.edu.agh.gg.HyperGraph;
import pl.edu.agh.gg.domain.Geom;
import pl.edu.agh.gg.domain.Rgb;
import pl.edu.agh.gg.domain.Vertex;
import pl.edu.agh.gg.domain.hyperEdge.HyperEdgeB;
import pl.edu.agh.gg.domain.hyperEdge.HyperEdgeI;

import java.awt.*;
import java.util.Arrays;

public class ElementApproxErrorTest {

    private class TestPurposeFakeImage extends FakeImage {

        private int testX;
        private int testY;

        TestPurposeFakeImage(int testX, int testY) {
            this.testX = testX;
            this.testY = testY;
        }

        @Override
        public int getRGB(int x, int y) {
            if (x == testX && y == testY) {
                return testedColor.getRGB();
            }
            return Color.gray.getRGB();
        }
    }

    private Rgb grayRgb = getRgbFromColor(Color.gray);
    private Vertex v1 = new Vertex(new Geom(0, 0), grayRgb, Vertex.Label.V);
    private Vertex v2 = new Vertex(new Geom(0, 10), grayRgb, Vertex.Label.V);
    private Vertex v3 = new Vertex(new Geom(10, 10), grayRgb, Vertex.Label.V);
    private Vertex v4 = new Vertex(new Geom(10, 0), grayRgb, Vertex.Label.V);
    private HyperEdgeB b1 = new HyperEdgeB(Arrays.asList(v1, v2));
    private HyperEdgeB b2 = new HyperEdgeB(Arrays.asList(v2, v4));
    private HyperEdgeB b3 = new HyperEdgeB(Arrays.asList(v4, v3));
    private HyperEdgeB b4 = new HyperEdgeB(Arrays.asList(v1, v3));
    private HyperEdgeI i = new HyperEdgeI(Arrays.asList(v1, v2, v3, v4), false);

    private ElementApproxError elementApproxErrorToTest;
    private FakeImage fakeImage;

    private Color testedColor = Color.cyan;

    private Rgb getRgbFromColor(Color color) {
        return new Rgb(color.getRed(), color.getGreen(), color.getBlue());
    }

    @Before
    public void setUp() {
        HyperGraph graph = new HyperGraph();
        graph.add(i);
        graph.add(v1);
        graph.add(v2);
        graph.add(v3);
        graph.add(v4);
        graph.add(b1);
        graph.add(b2);
        graph.add(b3);
        graph.add(b4);
        fakeImage = new TestPurposeFakeImage(-1, -1);
        elementApproxErrorToTest = new ElementApproxError(fakeImage);
    }

    @Test
    public void WhenPureColorGiven_ApproximationErrorEqualsZero() {
        Assert.assertEquals(0, elementApproxErrorToTest.calculateError(i), 0.0001);
    }

    @Test
    public void ErrorOfPixelsIsProperlyCalculated() {
        Assert.assertEquals(0, elementApproxErrorToTest.calculateError(i), 0.0001);
        int testedPixelX = 5;
        int testedPixelY = 5;
        fakeImage.setRGB(testedPixelX, testedPixelY, Color.cyan.getRGB());

        double fx = (testedPixelX - v1.getGeom().getX()) / 10;
        double nfx = 1 - fx;
        double fy = (testedPixelY - v3.getGeom().getY()) / 10;
        double nfy = 1 - fy;
        double diff_r = testedColor.getRed() - v1.getRgb().getR() * nfx * fy;
        double diff_g = testedColor.getBlue() - v1.getRgb().getG() * nfx * fy;
        double diff_b = testedColor.getGreen() - v1.getRgb().getB() * nfx * fy;
        diff_r -= v2.getRgb().getR() * fx * fy;
        diff_g -= v2.getRgb().getG() * fx * fy;
        diff_b -= v2.getRgb().getB() * fx * fy;
        diff_r -= v3.getRgb().getR() * nfx * nfy;
        diff_g -= v3.getRgb().getG() * nfx * nfy;
        diff_b -= v3.getRgb().getB() * nfx * nfy;
        diff_r -= v4.getRgb().getR() * fx * nfy;
        diff_g -= v4.getRgb().getG() * fx * nfy;
        diff_b -= v4.getRgb().getB() * fx * nfy;
        double expectedError = 0.5 * Math.pow(diff_r, 2) + 0.3 * Math.pow(diff_g, 2) + 0.2 * Math.pow(diff_b, 2);

        Assert.assertEquals(expectedError,
                new ElementApproxError(new TestPurposeFakeImage(testedPixelX, testedPixelY)
                ).calculateError(i), 0.0001);
    }

    @Test()
    public void WhenConnectedVerticesIsThree_DoesNotThrowException() {
        i.setConnectedVertices(Arrays.asList(v1, v2, v3));
        elementApproxErrorToTest.calculateError(i);
    }

    @Test(expected = CannotCalculateErrorException.class)
    public void WhenConnectedVerticesIsTwo_ThrowException() {
        i.setConnectedVertices(Arrays.asList(v1, v2));
        elementApproxErrorToTest.calculateError(i);
    }

}
