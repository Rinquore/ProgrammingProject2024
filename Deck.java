import java.io.BufferedWriter;
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.IOException;


public class Deck {
    private ArrayList<Integer> deck=new ArrayList<>();

    //constructor
    public Deck(){
    }

    public void addCard(int card){
        deck.add(card);
    }

    public int getCard(){
        int card=deck.get(0);
        deck.remove(0);
        return card;
        
    }

    public void writeToFile(int decknum){        
        try{
            BufferedWriter writer= new BufferedWriter(new FileWriter("deck_output"+decknum+".txt"));
            writer.write("deck"+decknum+" contents:");
            for (int i = 0; i < deck.size(); i++) {
                writer.write(" "+deck.get(i));
            }
            writer.close();
        } catch (IOException e) {
        }
    }
}
