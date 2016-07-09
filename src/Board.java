import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SuppressWarnings("supress")
public class Board extends JFrame{

    private int pairs = 3;
    private int[] gridSize = {2, 3};
    private List<Card> cards;
    private List<ImageIcon> icons;
    private Card selectedCard;
    private Card c1;
    private Card c2;
    private Timer t = new Timer(400, e -> {
        checkCards();
        updateHitMiss();
    });
    private int hits = 0;
    private int misses = 0;
    private int turns = 1;

    private Dimension d = new Dimension();
    private JMenuBar menuBar;
    private JMenu file, newGame, exit;
    private JMenuItem ngEasy, ngMedium, ngHard, ngCrazyMemes, instructions;
    private JLabel hitScore, missScore, turnsLabel;
    private JPanel scorePanel;
    private Container pane;
    private Container gridPane;
    private boolean isPaneEmpty = true;

    public void updateHitMiss() {
        hitScore.setText("Hits: " + hits);
        missScore.setText("Misses: " + misses);
        turnsLabel.setText("Turn: " + turns);
    }

    public Board(){

        makeMenu();

        t.setRepeats(false);

        turnsLabel = new JLabel("Turn: 1");
        hitScore = new JLabel("Hits: 0");
        missScore = new JLabel("Misses: 0");

        makeCards();

        setBoard();
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
            hits++;
            turns++;
            if (this.isGameWon()) {
                JOptionPane.showMessageDialog(this, "Winner winner chicken dinner!");
            }
        }

        else{
            c1.setIcon(null);
            c2.setIcon(null);
            misses++;
            turns++;
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

    public void deleteCards() {
        for (Card c : cards) {
            cards.remove(c);
        }
    }

    public void setBoard() {
        if (!isPaneEmpty) {
            pane.removeAll();
        }
        scorePanel = new JPanel();
        scorePanel.add(turnsLabel);
        scorePanel.add(hitScore);
        scorePanel.add(missScore);
        pane = getContentPane();
        gridPane = new Container();
        pane.add(gridPane, BorderLayout.CENTER);
        pane.add(menuBar, BorderLayout.NORTH);
        pane.add(scorePanel, BorderLayout.SOUTH);
        gridPane.setLayout(new GridLayout(gridSize[0], gridSize[1]));
        for (Card c : cards) {
            gridPane.add(c);
        }
        isPaneEmpty = false;
        setTitle("Meme Match Game");
    }

    public void makeCards() {
        List<Card> cardsList = new ArrayList<>();
        List<Integer> cardVals = new ArrayList<>();
        List<ImageIcon> imageIcons = new ArrayList<>();

        for (int i = 1; i <= pairs; i++) {
            ImageIcon ii = new ImageIcon(this.getClass().getResource("/images/thumbnails 150x150/meme"
                    + i + "thumb.jpg"));
            imageIcons.add(ii);
        }

        for (int j = 0; j < pairs; j++) {
            cardVals.add(j);
            cardVals.add(j);
        }
        Collections.shuffle(cardVals);

        for (int val : cardVals) {
            Card c = new Card();
            c.setBackground(Color.black);
            c.setId(val);
            c.setMeme(imageIcons.get(val));
            c.addActionListener(e -> {
                selectedCard = c;
                doTurn();
            });
            cardsList.add(c);
        }
        if (cards != null) {
            cards.clear();
        }
        if (icons != null) {
            icons.clear();
        }
        this.cards = cardsList;
        this.icons = imageIcons;


    }

    public void makeMenu() {
        menuBar = new JMenuBar();

        file = new JMenu("File");
        menuBar.add(file);

        exit = new JMenu("Exit");
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

        instructions = new JMenuItem("Instructions");
        instructions.addActionListener(e -> JOptionPane.showMessageDialog(this,
                "Welcome to Meme-ory Match!\n" +
                        "A fun take on a classic concentration game with pop culture images called memes.\n" +
                        "Concentration is a card game where all of the cards are shuffled and are laid face down on a surface in orderly rows and columns. All cards will have an image that matches on another card. \n" +
                        "The purpose of the game is to find all the matches.\n" +
                        "\n" +
                        "Instructions\n" +
                        "1.       Each turn the player(you) will select a card by clicking on a tile. An image will be displayed.\n" +
                        "2.       With the current image displayed the player must select another card/tile by clicking on another tile, not the tile currently selected.\n" +
                        "3.       If the two images selected match, the images will be greyed out and a match is found.\n" +
                        "4.       If the two images selected do not match, the images will be hidden again and the player will have to select to new images to find a match.\n" +
                        "5.       Player will continue selecting 2 cards until the player has found all of the matches.\n" +
                        "6.       Difficulty can be changed by selecting File->Difficulty-> and then player can choose preferred difficulty.\n" +
                        "7.       Difficulty is based off of cards. Easy has 6 cards 3 images. Medium has 16 cards with 8 images. Hard has 20 cards with 10 images.\u200B"
        ));
        file.add(instructions);

        newGame = new JMenu("New Game");
        file.add(newGame);

        ngEasy = new JMenuItem("Easy 2x3");
        ngEasy.addActionListener(e -> {
            System.out.println("Starting new game on easy level");
            pairs = 3;
            gridSize[0] = 2;
            gridSize[1] = 3;
            d = new Dimension(600, 600);
            this.setSize(d);
            makeCards();
            setBoard();
        });
        newGame.add(ngEasy);

        ngMedium = new JMenuItem("Medium 4x4");
        ngMedium.addActionListener(e -> {
            System.out.println("Starting new game on medium");
            pairs = 8;
            gridSize[0] = 4;
            gridSize[1] = 4;
            d = new Dimension(610, 700);
            this.setSize(d);

            makeCards();
            setBoard();
        });
        newGame.add(ngMedium);

        ngHard = new JMenuItem("Hard 4x5");
        ngHard.addActionListener(e -> {
            System.out.println("Starting new game on hard");
            pairs = 10;
            gridSize[0] = 4;
            gridSize[1] = 5;
            d = new Dimension(770, 690);
            this.setSize(d);

            makeCards();
            setBoard();
        });
        newGame.add(ngHard);

        ngCrazyMemes = new JMenuItem("Crazy 6x6");
        ngCrazyMemes.addActionListener(e -> {
            pairs = 18;
            gridSize[0] = 6;
            gridSize[1] = 6;
            d = new Dimension(920, 1000);
            this.setSize(d);

            makeCards();
            setBoard();
        });
        newGame.add(ngCrazyMemes);

    }

}