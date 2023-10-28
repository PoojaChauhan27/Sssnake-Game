import javax.swing.*;
import java.awt.*;

public class ScorePanel extends JTable{
    static int score;
    ScorePanel(){
        this.setPreferredSize(new Dimension(600,50));
        this.setBackground(Color.DARK_GRAY);
        this.setFocusable(true);
    }
    public void appleValue(int apples){
        score = apples;
        GameFrame.scorePanel.repaint();      // Request a repaint of the sorePanel -- request to erase and redraw of component
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        new MyTable(g);
    }
    static class MyTable extends GamePanel{
        MyTable(Graphics g){
            g.setColor(Color.lightGray);
            g.setFont(new Font("Ink Free", Font.BOLD,40));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Score: "+score, (SCREEN_WIDTH - metrics.stringWidth("Score: "+score))/2, g.getFont().getSize());
        }
    }
}


