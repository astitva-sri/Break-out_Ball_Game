package breakout_game;

import javax.swing.JPanel;
import javax.swing.Timer;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MyGame extends JPanel implements ActionListener, KeyListener{
    private boolean play = false;
    private int score = 0;
    private int totalBricks = 36;

    private Timer timer;
    private int delay = 8;

    private int playerX = 310;

    private int ballPosX = 120;
    private int ballPosY = 350;
    private int ballXdir = -1;
    private int ballYdir = -2;

    private MapGenerator map;

    public void paint(Graphics g) {

        g.setColor(Color.black);
        g.fillRect(1, 1, 692, 592);

        map.draw((Graphics2D) g);

        g.setColor(Color.yellow);
        g.fillRect(0, 0, 3, 592);
        g.fillRect(0, 0, 3, 592);
        g.fillRect(681, 0, 3, 592);

        g.setColor(Color.yellow);
        g.fillOval(ballPosX, ballPosY, 20, 20);

        // Draw paddle
        g.setColor(Color.RED); // Set the desired color 
        g.fillRect(playerX, 550, 100, 8);

        // Scores
        g.setColor(Color.WHITE);
        g.setColor(Color.white);
        g.setFont(new Font("serif", Font.BOLD, 25));
        g.drawString("" + score, 590, 30);

        // when you won the game
        if (totalBricks <= 0) {
            play = false;
            ballXdir = 0;
            ballYdir = 0;
            g.setColor(Color.RED);
            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("You Won", 260, 300);

            g.setColor(Color.RED);
            g.setFont(new Font("serif", Font.BOLD, 20));
            g.drawString("Press (Enter) to Restart", 230, 350);
        }

        // when you lose the game
        if (ballPosY > 570) {
            play = false;
            ballXdir = 0;
            ballYdir = 0;
            g.setColor(Color.RED);
            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("Game Over, Scores: " + score, 190, 300);

            g.setColor(Color.RED);
            g.setFont(new Font("serif", Font.BOLD, 20));
            g.drawString("Press (Enter) to Restart", 230, 350);
        }

    }

    MyGame() {

        map = new MapGenerator(4, 9);

        addKeyListener(this); // detect key events

        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        
        timer = new Timer(delay, this);
        timer.start(); // start timer
    }

    @Override
    public void keyPressed(KeyEvent e) {

        // right key is pressed
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            // check if doesn't go outside the panel
            if (playerX >= 600) {
                playerX = 600;
            } else {
                moveRight();
            }
        }

        // left key is pressed
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            // check if doesn't go outside the panel
            if (playerX < 10) {
                playerX = 10;
            } else {
                moveLeft();
            }
        }

        // if enter key is pressed
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!play) {
                // to restart
                play = true;
                ballPosX = 120;
                ballPosY = 350;
                ballXdir = -1;
                ballYdir = -2;
                playerX = 310;
                score = 0;
                totalBricks = 36;
                map = new MapGenerator(4, 9);

                repaint();
            }
        }
    }

    public void moveLeft() {
        play = true;
        playerX -= 15;
    }

    public void moveRight() {
        play = true;
        playerX += 15;
    }

    public void actionPerformed(ActionEvent e) {
        
            if (play) {
            // to move ball when game starts
            ballPosX += ballXdir;
            ballPosY += ballYdir;

            if (ballPosX < 0) {
                ballXdir = -ballXdir;
            }
            if (ballPosY < 0) {
                ballYdir = -ballYdir;
            }
            if (ballPosX > 670) {
                ballXdir = -ballXdir;
            }

            // collison of ball an paddle
            if (new Rectangle(ballPosX, ballPosY, 20, 20).intersects(new Rectangle(playerX, 550, 100, 8))) {
                ballYdir = -ballYdir;
            }

            // check map collision with ball
            // here in map.map.length first map is object we have created and second map is
            // the 2D array we have created
            A: for (int i = 0; i < map.map.length; i++) {
                for (int j = 0; j < map.map[0].length; j++) {
                    if (map.map[i][j] > 0) {

                        // for intersection we need to first detect the position of ball and brick with
                        // respect to height and width of the brick
                        int brickX = j * map.brickWidth + 80;
                        int brickY = i * map.brickHeight + 50;
                        int brickWidth = map.brickWidth;
                        int brickHeight = map.brickHeight;

                        Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
                        Rectangle ballRect = new Rectangle(ballPosX, ballPosY, 20, 20);
                        Rectangle brickRect = rect;

                        if (ballRect.intersects(brickRect)) {

                            map.setBrickValue(0, i, j);
                            score += 5;
                            totalBricks--;

                            // When ball hits right or left of brick
                            if (ballPosX + 19 <= brickRect.x || ballPosX + 1 >= brickRect.x + brickRect.width) {
                                ballXdir = -ballXdir;
                            }

                            // when ball hits bottom or top of brick
                            else {
                                ballYdir = -ballYdir;
                            }
                            break A;
                        }
                    }
                }
            }
            repaint();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Implementation not needed for this method
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Implementation not needed for this method
    }
}
