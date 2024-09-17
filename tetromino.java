import java.awt.*;
import java.util.Random;

public class tetromino {
    public static final Color[] COLORS = {Color.CYAN, Color.BLUE, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.PINK, Color.MAGENTA};
    private int[][] shape;
    private Color color;

    public tetromino(int[][] shape, Color color) {
        this.shape = shape;
        this.color = color;
    }

    public int[][] getShape() {
        return shape;
    }

    public Color getColor() {
        return color;
    }

    public void setShape(int[][] shape) {
        this.shape = shape;
    }

    public int[][] rotate() {
        int[][] rotated = new int[shape[0].length][shape.length];
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[i].length; j++) {
                rotated[j][shape.length - 1 - i] = shape[i][j];
            }
        }
        return rotated;
    }

    public static tetromino randomTetromino() {
        Random rand = new Random();
        switch (rand.nextInt(7)) {
            case 0: return new tetromino(new int[][]{{1, 1, 1, 1}}, COLORS[0]); // I
            case 1: return new tetromino(new int[][]{{1, 1, 1}, {0, 1, 0}}, COLORS[1]); // T
            case 2: return new tetromino(new int[][]{{1, 1}, {1, 1}}, COLORS[2]); // O
            case 3: return new tetromino(new int[][]{{1, 1, 0}, {0, 1, 1}}, COLORS[3]); // S
            case 4: return new tetromino(new int[][]{{0, 1, 1}, {1, 1, 0}}, COLORS[4]); // Z
            case 5: return new tetromino(new int[][]{{1, 1, 1}, {1, 0, 0}}, COLORS[5]); // L
            case 6: return new tetromino(new int[][]{{1, 1, 1}, {0, 0, 1}}, COLORS[6]); // J
            default: return null;
        }
    }
}
