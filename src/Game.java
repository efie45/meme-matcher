import java.awt.Dimension;
import javax.swing.JFrame;


public class Game{
    public static void main(String[] args){
        Board b = new Board();
        b.setPreferredSize(new Dimension(600,600)); //need to use this instead of setSize
        b.setLocation(500, 250);
        b.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        b.pack();
        b.setVisible(true);
    }
}
