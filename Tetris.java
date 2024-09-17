import javax.swing.*;

public class Tetris {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Tetris");
        gameboard board = new gameboard();
        frame.add(board);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 600);
        frame.setResizable(false);
        frame.setVisible(true);
        board.startGame();
    }
}
