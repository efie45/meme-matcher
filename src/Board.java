import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SuppressWarnings("serial")
public class Board extends JFrame{


    private List<Card> cards;
    private List<ImageIcon> icons;
    private Card selectedCard;
    private Card c1;
    private Card c2;
    private Timer t = new Timer(750, e -> {
        checkCards();
    });

    public Board(){

        int pairs = 8;
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