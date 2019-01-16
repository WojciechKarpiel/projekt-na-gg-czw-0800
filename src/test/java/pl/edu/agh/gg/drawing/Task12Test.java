package pl.edu.agh.gg.drawing;

import org.junit.Assert;
import org.junit.Test;

import java.awt.*;

public class Task12Test {

    int x1 = 0;
    int y1 = 0;
    int x2 = 100;
    int y2 = 100;

    @Test
    public void returnsRedBitmap() {
        int redR = Color.RED.getRed();
        int redG = Color.RED.getGreen();
        int redB = Color.RED.getBlue();
        double[][][] approx = Task12.approx(x1, y1, x2, y2, redR, redR, redR, redR, redG, redG, redG, redG, redB, redB, redB, redB);
        checkBitmap(redR, redG, redB, approx);
    }

    @Test
    public void returnsGreenBitmap() {
        int greenR = Color.GREEN.getRed();
        int greenG = Color.GREEN.getGreen();
        int greenB = Color.GREEN.getBlue();
        double[][][] approx = Task12.approx(x1, y1, x2, y2, greenR, greenR, greenR, greenR, greenG, greenG, greenG, greenG, greenB, greenB, greenB, greenB);
        checkBitmap(greenR, greenG, greenB, approx);
    }

    @Test
    public void returnsBlueBitmap() {
        int blueR = Color.BLUE.getRed();
        int blueG = Color.BLUE.getGreen();
        int blueB = Color.BLUE.getBlue();
        double[][][] approx = Task12.approx(x1, y1, x2, y2, blueR, blueR, blueR, blueR, blueG, blueG, blueG, blueG, blueB, blueB, blueB, blueB);
        checkBitmap(blueR, blueG, blueB, approx);
    }

    @Test
    public void returnsWhiteBitmap() {
        int whiteR = Color.WHITE.getRed();
        int whiteG = Color.WHITE.getGreen();
        int whiteB = Color.WHITE.getBlue();
        double[][][] approx = Task12.approx(x1, y1, x2, y2, whiteR, whiteR, whiteR, whiteR, whiteG, whiteG, whiteG, whiteG, whiteB, whiteB, whiteB, whiteB);
        checkBitmap(whiteR, whiteG, whiteB, approx);
    }

    @Test
    public void returnsBlackBitmap() {
        int blackR = Color.BLACK.getRed();
        int blackG = Color.BLACK.getGreen();
        int blackB = Color.BLACK.getBlue();
        double[][][] approx = Task12.approx(x1, y1, x2, y2, blackR, blackR, blackR, blackR, blackG, blackG, blackG, blackG, blackB, blackB, blackB, blackB);
        checkBitmap(blackR, blackG, blackB, approx);
    }

    private void checkBitmap(int redR, int redG, int redB, double[][][] approx) {
        for (int i = x1; i < x2; i++) {
            for (int j = y1; j < y2; j++) {
                Assert.assertEquals(Math.round(approx[0][i][j]), redR);
                Assert.assertEquals(Math.round(approx[1][i][j]), redG);
                Assert.assertEquals(Math.round(approx[2][i][j]), redB);
            }
        }
    }
}
