import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {

    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE = 25;
    static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT/UNIT_SIZE);
    static final int DELAY = 95;                        // how fast the game is running
    final int x[] = new int[GAME_UNITS];
    final int y[] = new int[GAME_UNITS];
    int bodyParts = 6;
    int applesEaten;
    int appleX;
    int appleY;
    char direction = 'R';
    boolean running = false;
    Timer timer;
    Random random;
    static int apple;
    GamePanel(){
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
    }
    public void startGame(){
        newApple();                               // when game opens create first new apple on screen
        running = true;                           // ready - running true from false
        timer = new Timer(DELAY,this);    // set - running at certain pace we defined in DELAY
        timer.start();                           // go - start the game
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g){
        if(running){
            //drawing the cells in which snake will move
            /*g.setColor(Color.gray);
            for(int i=0;i<SCREEN_HEIGHT/UNIT_SIZE;i++){
               g.drawLine(i*UNIT_SIZE,0, i*UNIT_SIZE, SCREEN_HEIGHT);
               g.drawLine(0, i*UNIT_SIZE, SCREEN_WIDTH, i*UNIT_SIZE);
            }*/
            GameFrame.gamePanel.requestFocusInWindow();
               g.setColor(Color.red);                           // Coloring the apple red
               g.fillOval(appleX,appleY,UNIT_SIZE,UNIT_SIZE);   // shaping the apple oval

            for(int i=0; i<bodyParts; i++ ) {                      // drawing the snake -- head and body
                if (i == 0) {                                      // drawing head of the snake
                    g.setColor(Color.green);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                } else {                                            // drawing body of snake
                    //g.setColor(new Color(45, 180, 0));
                    g.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));  //multicolor snake
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
            }
            /*g.setColor(Color.red);
            g.setFont(new Font("Ink Free", Font.BOLD,40));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Score: "+applesEaten, (SCREEN_WIDTH - metrics.stringWidth("Score: "+applesEaten))/2, g.getFont().getSize());*/
            apple = applesEaten;
            ScorePanel sP = new ScorePanel();
            sP.appleValue(apple);
        }else{
            gameOver(g);
        }
    }
    public void newApple(){                           //generating the coordinates of apple-- anytime we begin the game or score a point or snake eat an apple
        appleX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
        appleY = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
    }
    public void move(){                              // moving the snake through for loop
        for(int i = bodyParts; i>0; i--){
            x[i] = x[i-1];
            y[i] = y[i-1];
        }
        switch(direction){                          // moving snake up down left right on x & y coordinates
            case('U'):
                y[0] = y[0] - UNIT_SIZE;
                break;
            case('D'):
                y[0] = y[0] + UNIT_SIZE;
                break;
            case('R'):
                x[0] = x[0] + UNIT_SIZE;
                break;
            case('L'):
                x[0] = x[0] - UNIT_SIZE;
                break;
        }
    }
    public void checkApple(){                      // check if snake eats apple
        if((x[0]==appleX) && (y[0]==appleY)){
            bodyParts++;                           // if snake ate apple increase the snake length by 1 unit
            applesEaten++;                         // if snake ate apple than increase the score by 1;
            newApple();                            // after apple is eaten by snake create a new apple
        }
    }
    public void checkCollisions(){                 // it checks if snake collide with the walls or its body itself
        for(int i=bodyParts; i>0; i--){
            if((x[0]==x[i]) && (y[0]==y[i])){      // this condition means snake collided with its body
                running = false;                  // stop running the snake
            }
        }
        if(x[0] < 0){                            // checks if snake collided with the LEFT border/wall
            running = false;                     // stop running the snake
        }
        if(x[0] > SCREEN_WIDTH){                  // checks if snake collided with the RIGHT border/wall
            running = false;
        }
        if(y[0] < 0){                             // checks if snake collided with the TOP border/wall
            running = false;
        }
        if(y[0] > SCREEN_HEIGHT){                 // checks if snake collided with the BOTTOM border/wall
            running = false;
        }
        if(!running){                            // if snake stops running than stop the game
            timer.stop();
        }
    }
    public void gameOver(Graphics g){
        //Score
        /*g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD,40));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("Score: "+applesEaten, (SCREEN_WIDTH - metrics1.stringWidth("Score: "+applesEaten))/2, g.getFont().getSize());*/
        //game over text
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD,75));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("Game Over", (SCREEN_WIDTH - metrics2.stringWidth("Game Over"))/2, SCREEN_HEIGHT/2);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(running){                                // if snake is running
            move();                                 // then move the snake
            checkApple();                           // after snake starts moving look for apple
            checkCollisions();                      // after snake starts moving look for collisions to walls or snakes body
        }
        repaint();                                 // if snake is no longer running repaint the screen
    }
    public class MyKeyAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e){          // controlling the snake movements on screen when user press keys
            switch (e.getKeyCode()){
                case KeyEvent.VK_LEFT:
                    if(direction != 'R'){
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if(direction != 'L'){
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if(direction != 'D'){
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if(direction != 'U'){
                        direction = 'D';
                    }
                    break;
            }
        }
    }
}
