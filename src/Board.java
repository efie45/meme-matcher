import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SuppressWarnings("serial")
public class Board extends JFrame{

    private String difficulty;
    private int pairs;
    private int[] gridSize = {3, 4};
    private List<Card> cards;
    private List<ImageIcon> icons;
    private Card selectedCard;
    private Card c1;
    private Card c2;
    private Timer t = new Timer(750, e -> {
        checkCards();
    });
    JMenuBar menuBar;
    JMenu file, newGame, exit;
    JMenuItem ngEasy, ngMedium, ngHard;

    public Board(){

        menuBar = new JMenuBar();

        file = new JMenu("File");
        menuBar.add(file);

        exit = new JMenu("Exit (Press X)");
        exit.setMnemonic(KeyEvent.VK_X);
        exit.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                System.exit(0);
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
        exit.addMenuListener(new MenuListener() {
            @Override
            public void menuSelected(MenuEvent e) {
                System.exit(0);
            }

            @Override
            public void menuDeselected(MenuEvent e) {
            }

            @Override
            public void menuCanceled(MenuEvent e) {
            }
        });
        menuBar.add(exit);

        newGame = new JMenu("New Game");
        file.add(newGame);

        ngEasy = new JMenuItem("Easy 2x3");
        ngEasy.addActionListener(e -> {
            System.out.println("Starting new game on easy level");
            difficulty = "easy";
            pairs = 3;
            gridSize[0] = 2;
            gridSize[1] = 3;
        });
        newGame.add(ngEasy);

        ngMedium = new JMenuItem("Medium 4x4");
        ngMedium.addActionListener(e -> {
            System.out.println("Starting new game on medium");
            difficulty = "medium";
            pairs = 8;
        });
        newGame.add(ngMedium);

        ngHard = new JMenuItem("Hard 4x5");
        ngHard.addActionListener(e -> {
            System.out.println("Starting new game on hard");
            difficulty = "hard";
            pairs = 10;
            gridSize[0] = 4;
            gridSize[1] = 5;
        });
        newGame.add(ngHard);


        List<Card> cardsList = new ArrayList<>();
        List<Integer> cardVals = new ArrayList<>();
        List<ImageIcon> imageIcons = new ArrayList<>();

        for (int i = 1; i < pairs + 1; i++){
            ImageIcon ii = new ImageIcon(this.getClass().getResource("/images/thumbnails 150x150/meme"
                    + i + "thumb.jpg"));
            imageIcons.add(ii);
        }

        for (int j = 0; j < pairs; j++){
            cardVals.add(j);
            cardVals.add(j);
        }
        Collections.shuffle(cardVals);

        for (int val : cardVals){
            Card c = new Card();
            c.setId(val);
            c.setMeme(imageIcons.get(val));
            c.addActionListener(e -> {
                selectedCard = c;
                doTurn();
            });
            cardsList.add(c);
        }

        this.cards = cardsList;
        this.icons = imageIcons;

        //set up the timer

        t.setRepeats(false);

        //set up the board itself
        this.setJMenuBar(menuBar);
        Container pane = getContentPane();
        pane.setLayout(new GridLayout(4, 5));
        for (Card c : cards){
            pane.add(c);
        }
        setTitle("Meme Match Game");
    }

    public void doTurn(){
        if (c1 == null && c2 == null){
            c1 = selectedCard;
            c1.setIcon(c1.getMeme());
        }

        if (c1 != null && c1 != selectedCard && c2 == null){
            c2 = selectedCard;
            c2.setIcon(c2.getMeme());
            t.start();

        }
    }

    public void checkCards(){
        if (c1.getId() == c2.getId()){//match condition
            c1.setEnabled(false); //disables the button
            c2.setEnabled(false);
            c1.setMatched(true); //flags the button as having been matched
            c2.setMatched(true);
            if (this.isGameWon()){
                JOptionPane.showMessageDialog(this, "You win!");
                System.exit(0);
            }
        }

        else{
            c1.setIcon(null);
            c2.setIcon(null);
        }
        c1 = null; //reset c1 and c2
        c2 = null;
    }

    public boolean isGameWon(){
        for(Card c: this.cards){
            if (!c.getMatched()) return false;
            continue;
        }
        return true;
    }

}