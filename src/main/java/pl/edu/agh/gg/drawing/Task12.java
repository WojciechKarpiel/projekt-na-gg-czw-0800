package pl.edu.agh.gg.drawing;

public class Task12 {
    public static double[][] APPROX_R;
    public static double[][] APPROX_G;
    public static double[][] APPROX_B;

    /**
     * r1 - x1,y2
     * r2 - x2,y2
     * r3 - x1,y1
     * r4 - x2,y1
     */
    public static void approx(int x1, int y1, int x2, int y2,
                              int r1, int r2, int r3, int r4,
                              int g1, int g2, int g3, int g4,
                              int b1, int b2, int b3, int b4) {
        APPROX_R = new double[x2][y2];
        APPROX_G = new double[x2][y2];
        APPROX_B = new double[x2][y2];
        for (int px = x1; px < x2; px++) {
            for (int py = y1; py < y2; py++) {
                APPROX_R[px][py] = 0.0;
                APPROX_G[px][py] = 0.0;
                APPROX_B[px][py] = 0.0;

                APPROX_R[px][py] += r1 * (1.0 - (1.0 * (px - x1) / (x2 - x1))) * (1.0 * (py - y1) / (y2 - y1));
                APPROX_G[px][py] += g1 * (1.0 - (1.0 * (px - x1) / (x2 - x1))) * (1.0 * (py - y1) / (y2 - y1));
                APPROX_B[px][py] += b1 * (1.0 - (1.0 * (px - x1) / (x2 - x1))) * (1.0 * (py - y1) / (y2 - y1));

                APPROX_R[px][py] += r2 * ((1.0 * (px - x1) / (x2 - x1))) * (1.0 * (py - y1) / (y2 - y1));
                APPROX_G[px][py] += g2 * ((1.0 * (px - x1) / (x2 - x1))) * (1.0 * (py - y1) / (y2 - y1));
                APPROX_B[px][py] += b2 * ((1.0 * (px - x1) / (x2 - x1))) * (1.0 * (py - y1) / (y2 - y1));

                APPROX_R[px][py] += r3 * (1.0 - (1.0 * (px - x1) / (x2 - x1))) * (1.0 - (1.0 * (py - y1) / (y2 - y1)));
                APPROX_G[px][py] += g3 * (1.0 - (1.0 * (px - x1) / (x2 - x1))) * (1.0 - (1.0 * (py - y1) / (y2 - y1)));
                APPROX_B[px][py] += b3 * (1.0 - (1.0 * (px - x1) / (x2 - x1))) * (1.0 - (1.0 * (py - y1) / (y2 - y1)));

                APPROX_R[px][py] += r4 * (1.0 * (px - x1) / (x2 - x1)) * (1.0 - (1.0 * (py - y1) / (y2 - y1)));
                APPROX_G[px][py] += g4 * (1.0 * (px - x1) / (x2 - x1)) * (1.0 - (1.0 * (py - y1) / (y2 - y1)));
                APPROX_B[px][py] += b4 * (1.0 * (px - x1) / (x2 - x1)) * (1.0 - (1.0 * (py - y1) / (y2 - y1)));
            }
        }
    }

    public static void main(String[] args) {
        int x1 = 0;
        int y1 = 0;
        int x2 = 1000;
        int y2 = 1000;
        APPROX_R = new double[x2][y2];
        APPROX_G = new double[x2][y2];
        APPROX_B = new double[x2][y2];

        approx(x1, y1, x2, y2, 0, 100, 200, 256, 0, 50, 80, 100, 256, 50, 100, 150);
        Drawer.createPicture(x1, y1, x2, y2, APPROX_R, APPROX_G, APPROX_B,"obrazek.bmp");

    }
}
