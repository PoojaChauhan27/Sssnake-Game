import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class SnakeGame extends GameFrame{
    public static void main(String[] args){
        GameFrame frame = new GameFrame();    // shortcut -- new GameFrame();
        frame.setTitle("Snake");
        frame.setResizable(false);
        frame.setLayout(new BorderLayout());
        frame.setSize(600,650);
        frame.setLocation(200,200);
        frame.add(gamePanel, BorderLayout.SOUTH);
        frame.add(scorePanel, BorderLayout.NORTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
