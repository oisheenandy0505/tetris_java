import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Arrays;

public class gameboard extends JPanel implements ActionListener {
    private final int BOARD_WIDTH = 10;
    private final int BOARD_HEIGHT = 20;
    private final int[][] board = new int[BOARD_HEIGHT][BOARD_WIDTH];
    private final Timer timer;
    private boolean isGameOver = false;
    private tetromino currentTetromino;
    private int currentX, currentY;

    public gameboard() {
        setFocusable(true);
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (isGameOver) return;
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT -> moveLeft();
                    case KeyEvent.VK_RIGHT -> moveRight();
                    case KeyEvent.VK_DOWN -> drop();
                    case KeyEvent.VK_UP -> rotate();
                }
                repaint();
            }
        });
        timer = new Timer(500, this);
    }

    public void startGame() {
        spawnTetromino();
        timer.start();
    }

    private void spawnTetromino() {
        currentTetromino = tetromino.randomTetromino();
        currentX = BOARD_WIDTH / 2 - 1;
        currentY = 0;
        if (!isValidMove(currentTetromino.getShape(), currentX, currentY)) {
            isGameOver = true;
        }
    }

    private void moveLeft() {
        if (isValidMove(currentTetromino.getShape(), currentX - 1, currentY)) {
            currentX--;
        }
    }

    private void moveRight() {
        if (isValidMove(currentTetromino.getShape(), currentX + 1, currentY)) {
            currentX++;
        }
    }

    private void drop() {
        if (isValidMove(currentTetromino.getShape(), currentX, currentY + 1)) {
            currentY++;
        } else {
            lockTetromino();
            clearLines();
            spawnTetromino();
        }
    }

    private void rotate() {
        int[][] rotatedShape = currentTetromino.rotate();
        if (isValidMove(rotatedShape, currentX, currentY)) {
            currentTetromino.setShape(rotatedShape);
        }
    }

    private boolean isValidMove(int[][] shape, int x, int y) {
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[i].length; j++) {
                if (shape[i][j] != 0) {
                    int boardX = x + j;
                    int boardY = y + i;
                    if (boardX < 0 || boardX >= BOARD_WIDTH || boardY >= BOARD_HEIGHT || board[boardY][boardX] != 0) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private void lockTetromino() {
        for (int i = 0; i < currentTetromino.getShape().length; i++) {
            for (int j = 0; j < currentTetromino.getShape()[i].length; j++) {
                if (currentTetromino.getShape()[i][j] != 0) {
                    board[currentY + i][currentX + j] = currentTetromino.getShape()[i][j];
                }
            }
        }
    }

    private void clearLines() {
        for (int i = 0; i < BOARD_HEIGHT; i++) {
            if (Arrays.stream(board[i]).allMatch(cell -> cell != 0)) {
                for (int j = i; j > 0; j--) {
                    board[j] = board[j - 1];
                }
                board[0] = new int[BOARD_WIDTH]; // Clear the top line
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        drop();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

        // Draw the board
        for (int i = 0; i < BOARD_HEIGHT; i++) {
            for (int j = 0; j < BOARD_WIDTH; j++) {
                if (board[i][j] != 0) {
                    g.setColor(tetromino.COLORS[board[i][j] - 1]);
                    g.fillRect(j * 30, i * 30, 30, 30);
                }
            }
        }

        // Draw the current tetromino
        g.setColor(currentTetromino.getColor());
        for (int i = 0; i < currentTetromino.getShape().length; i++) {
            for (int j = 0; j < currentTetromino.getShape()[i].length; j++) {
                if (currentTetromino.getShape()[i][j] != 0) {
                    g.fillRect((currentX + j) * 30, (currentY + i) * 30, 30, 30);
                }
            }
        }

        if (isGameOver) {
            g.setColor(Color.RED);
            g.drawString("Game Over", 100, 250);
        }
    }
}
