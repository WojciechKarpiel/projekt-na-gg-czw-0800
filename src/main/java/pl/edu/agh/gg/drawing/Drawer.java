package pl.edu.agh.gg.drawing;

import pl.edu.agh.gg.approximation.Approximation;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

class Drawer {

    static void createPicture(Approximation approximation, String fileName) {
        createPicture(0, 0, approximation.getMaxX(), approximation.getMaxY(), approximation.getApproxR(),
                approximation.getApproxG(), approximation.getApproxB(), fileName);
    }

    static void createPicture(
            int x1, int y1, int x2, int y2,
            double[][] approxR, double[][] approxG, double[][] approxB,
            String fileName) {
        BufferedImage img = new BufferedImage(x2 - x1, y2 - y1, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < x2 - x1; x++) {
            for (int y = 0; y < y2 - y1; y++) {
                int rByte = (int) Math.round(approxR[x + x1][y + y1]);
                int gByte = (int) Math.round(approxG[x + x1][y + y1]);
                int bByte = (int) Math.round(approxB[x + x1][y + y1]);
                int rgb = (rByte << 16) | (gByte << 8) | bByte;
                img.setRGB(x, y, rgb);
            }
        }
        try {
            ImageIO.write(img, "bmp", new File(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
