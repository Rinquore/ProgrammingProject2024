import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Deck{
    public ArrayList<Integer> deck=new ArrayList<>();
    public Deck(){}

    public void addCard(int card, boolean bottom){
        if(bottom){
            deck.add(0, card);
        }else{
            deck.add(card);
        }
    }
    public int getCard(){
        int card=deck.get(deck.size()-1);
        deck.remove(deck.size()-1);
        return card;

    }
    public void writeDeckFile(int decknum){ //print out deck to a file
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("Deck"+decknum+"_output.txt"));
            writer.write("deck"+decknum+" contents:");
            for (int i = 0; i < deck.size(); i++) {
                writer.write(" "+Integer.toString(deck.get(i)));
            }
            writer.close();
        } catch (IOException e) {
        }
    }
}
