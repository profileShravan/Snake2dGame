import javax.swing.*;


public class SnakeGame extends JFrame {
    Board board;



    SnakeGame(){
        board = new Board();
        add(board);
        pack();  // ths will resize the frame to given size
        setResizable(false);
        setVisible(true);

    }
    public static void main(String[] args) {
 // Initialize snake game
     SnakeGame snakegame = new SnakeGame();
    }
}