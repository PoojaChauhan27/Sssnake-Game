import javax.swing.*;
public class GameFrame extends JFrame {
    public static GamePanel gamePanel;
    public static ScorePanel scorePanel;

    GameFrame(){
         gamePanel = new GamePanel();
         {
             this.add(gamePanel);                         // shortcut -- this.add(new GamePanel());
         }
         scorePanel = new ScorePanel();
         {
             this.add(scorePanel);
         }
    }
}
