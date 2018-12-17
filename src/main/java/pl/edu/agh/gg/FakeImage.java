package pl.edu.agh.gg;

import java.awt.*;
import java.awt.image.BufferedImage;

public class FakeImage extends BufferedImage {
    public FakeImage() {
        super(10, 20, 1);
    }

    @Override
    public int getWidth() {
        return 100;
    }

    @Override
    public int getHeight() {
        return 80;
    }

    @Override
    public int getRGB(int i, int i1) {
        return Color.orange.getRGB();
    }
}
