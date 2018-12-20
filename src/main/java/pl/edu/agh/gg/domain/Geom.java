package pl.edu.agh.gg.domain;

import java.util.Objects;

public class Geom {
    private final int x;
    private final int y;

    public Geom(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Geom geom = (Geom) o;
        return x == geom.x &&
                y == geom.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "Geom{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
