import javax.swing.*;
import java.awt.*;


public class Game{
    public static void main(String[] args){
        Board b = new Board();
        b.setPreferredSize(new Dimension(500, 375)); //need to use this instead of setSize
        b.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        b.pack();
        b.setVisible(true);
    }
}
