import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.CyclicBarrier;

/*assumptions this code makes of which I am not sure we are allowed to make:
-minimum value of n is 2. negatives dont make sense, 0 neither and 1 goes on in an infinite loop
-a valid deck contains no 0's. the specification is vague, saying all numbers must be positive. idk if 0 is counted as a positive number
---
other assumptions which are given in the specification:
-a valid pack needs to have no negative values, be 8n rows long and have each row be a +ve integer
-we Only need a Deck and Player class
-we dont need to handle players winning at the same time
-we dont need to check if a deck pack allows for a win, we should assume it does/ only test if it works when the pack does allow a win.
-discarding should be random with the only exception being the preferred card.




are the decks supposed to be stacks(you pick from the top) or can you pick a random card from them??
*/
public class main {
    public static void main(String[] args){

        //check if inputs are valid
        Scanner scanner= new Scanner(System.in);
        while(args.length!=2 || validInputs(args[0], args[1])<2){ 
            System.out.println("Invalid arguments, please specify a valid player number(at least 2) and a valid deck.txt file"+
                               " such as \"4 deckfile.txt\": ");
            args= scanner.nextLine().split(" ");
        }
        scanner.close();

        //define players and decks
        int n=Integer.parseInt(args[0]);
        Player.threads=n;
        Player.barrier=new CyclicBarrier(n);
        Player[] players =new Player[n];
        Deck[] decks=new Deck[n];
        for (int i = 0; i < n; i++) {
            decks[i]=new Deck();
        }
        for (int i = 0; i < n; i++) {
            players[i]= new Player(i+1, decks[i], decks[(i+1)%n]);            
        }

        //distribute cards to players and decks before threads start in a round-robin fashion.
        try{
        BufferedReader reader = new BufferedReader(new FileReader(args[1]));
        for(int i=0; i<4;i++){
            for (int j = 0; j < n; j++) {
                String line=reader.readLine();
                players[j].hand[i]=Integer.parseInt(line);
            }
        }
        for(int i=0; i<4*n;i++){
            String line=reader.readLine();
            decks[i%n].AddCard(Integer.parseInt(line),false);
        }
        reader.close();
        }catch (IOException e) {}

        //write to the outputfiles string the hands dealt
        //start threads, check for winners at the start of threads
        for(Player player : players){
            player.start();
        }

    }





    public static int validInputs(String value, String txt){ //returns n if inputs are valid and 0 if they are not. any n<0 is also considered invalid
        int n=0;
        try {
            n=Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return 0;
        }

        try {
            BufferedReader reader= new BufferedReader(new FileReader(txt));
            String line=reader.readLine();
            int card_num=0;
            //used to make sure each player has their favourite card number appear at least 4 times
            int card=0;
            while(line!=null){
                card_num++;
                card=Integer.parseInt(line);
                if(card<=0){reader.close(); return 0;}  //negative cards not allowed or bigger than 4n;
                line=reader.readLine();
            }
            reader.close();
            if(card_num!=n*8){return 0;}    //checks if deck size is 8n as needed
            return n;
        } catch (FileNotFoundException |NumberFormatException e) {
            return 0;
        }catch(IOException e){
            return 0;
        }
    }
}