import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.CyclicBarrier;

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
        //distribute cards to players and decks in a round-robin fashion before threads start
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
            decks[i%n].addCard(Integer.parseInt(line),false);
        }
        reader.close();
        }catch (IOException e) {}
        //start threads
        for(Player player : players){
            player.start();
        }

    }





    public static int validInputs(String value, String txt){ //returns n if inputs are valid and 0 if they are not. any n<0 is considered invalid
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