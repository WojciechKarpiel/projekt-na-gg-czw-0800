package pl.edu.agh.gg.approximation;

public class Approximation {
    private int maxX;
    private int maxY;

    private double[][] approxR;
    private double[][] approxG;
    private double[][] approxB;

    public Approximation(double[][] R, double[][] G, double[][] B) {
        approxR = R;
        approxG = G;
        approxB = B;
        maxX = R.length;
        maxY = R[0].length;
    }

    public double[][] getApproxR() {
        return approxR;
    }

    public double[][] getApproxG() {
        return approxG;
    }

    public double[][] getApproxB() {
        return approxB;
    }

    public int getMaxX() {
        return maxX;
    }

    public int getMaxY() {
        return maxY;
    }
}
