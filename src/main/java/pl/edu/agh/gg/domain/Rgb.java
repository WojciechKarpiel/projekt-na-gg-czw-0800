package pl.edu.agh.gg.domain;

import java.util.Objects;

public class Rgb {
    private final int r;
    private final int g;
    private final int b;

    // teoretycznie to po bajcie wystarczy ale w Dżawie mają znaki
    public Rgb(int r, int g, int b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rgb rgb = (Rgb) o;
        return r == rgb.r &&
                g == rgb.g &&
                b == rgb.b;
    }

    @Override
    public int hashCode() {
        return Objects.hash(r, g, b);
    }

    public int getR() {
        return r;
    }

    public int getG() {
        return g;
    }

    public int getB() {
        return b;
    }

    @Override
    public String toString() {
        return "Rgb{" +
                "r=" + r +
                ", g=" + g +
                ", b=" + b +
                '}';
    }
}

