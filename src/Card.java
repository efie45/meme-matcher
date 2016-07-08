import javax.swing.*;

@SuppressWarnings("serial")
public class Card extends JButton{
    private int id;
    private boolean matched = false;
    private ImageIcon meme;

    public void setMeme(ImageIcon i)
    {
        this.meme = i;
    }

    public ImageIcon getMeme()
    {
        return meme;
    }

    public void setId(int id){
        this.id = id;
    }

    public int getId(){
        return this.id;
    }

    public void setMatched(boolean matched){
        this.matched = matched;
    }

    public boolean getMatched(){
        return this.matched;
    }
}