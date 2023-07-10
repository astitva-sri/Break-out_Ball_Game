package breakout_game;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
public class Main {
    public static void main(String[] args){

        JFrame frame = new JFrame();

        MyGame game = new MyGame();

        frame.setBounds(200, 100, 700, 600);
        frame.setTitle("Breakout Ball");
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.add(game);

        ImageIcon icon = new ImageIcon("static\\icon.png");
        frame.setIconImage(icon.getImage());

        frame.addKeyListener(game);
        

    }
}