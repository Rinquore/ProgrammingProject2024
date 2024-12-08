import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class main {
    public static void main(String[] args){
        //check if inputs are valid
        Scanner scanner= new Scanner(System.in);
        while(args.length!=2 || validInputs(args[0], args[1])<1){ 
            System.out.println("Invalid arguments, please specify a valid player number(at least 2) and a valid deck.txt file"+
                               " such as \"4 deckfile.txt\": ");
            args= scanner.nextLine().split(" ");
        }
        scanner.close();
        int n=args[0];
        Deck[] decks= new Deck[n];
        for (int i = 0; i < n; i++) {
            decks[i]=new Deck();
        }
        Player[] players = new Player[n];
        for(int i=0; i<n; i++){
            players[i]= new Player(i+1, decks[i], decks[(i+1)%n])
        }

        //define Players

        //go through deck file and distribute cards
        BufferedReader reader = new BufferedReader(new FileReader(txt));
        for (int j = 0; j < 4; j++) {
            for (int j2 = 0; j2 < n; j2++) {
                Players[j2].hand[j]=Integer.parseInt(reader.readLine());
            }
        }
        for (int j2 = 0; j2 < 4; j2++) {
            for (int k = 0; k < n; k++) {
                Decks[k].addCard[reader.nextLine()];
            }
        }
        reader.close();
        //start threads
        //create output files..
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
