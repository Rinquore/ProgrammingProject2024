import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

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

        //define Decks
        //define Players
        //go through deck file and distribute cards
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