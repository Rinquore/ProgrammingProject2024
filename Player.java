import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Player extends Thread{
    public static CyclicBarrier barrier;
    private int favourite;
    private Deck drawing_deck;
    private Deck discarding_deck;
    private String fileoutput="";
    public int[] hand={-1,-1,-1,-1,-1};
    private int completion=0;
    public static volatile int won=-1;
    public static int threads=0;

    public Player(int favourite, Deck drawing_deck, Deck discarding_deck ){
        this.favourite=favourite;
        this.discarding_deck=discarding_deck;
        this.drawing_deck=drawing_deck;
    }
    @Override
    public void run(){
        int winning=-1;
        //adds initial hand to output string
        fileoutput+="Player "+ favourite+ " initial hand";
        for (int i = 0; i < hand.length-1; i++) {
            fileoutput+=" "+hand[i];
        }
        fileoutput+="\n";

        //puts any favourite cards on the left side of the hand(start of the array). Useful at start of game, 
        //and makes choosing a random card to discard easier. once completion==4, the game is won.
        
        for (int i = 0; i < hand.length-1; i++) {
            if(hand[i]==favourite && won==-1){
                hand[i]=hand[completion];
                hand[completion]=favourite;
                completion++;
            }
        }

        winning=winningHand();
        if(winning!=-1 || completion==4){   //check if current hand is a winning hand
            won=favourite;
        }

        try{
            barrier.await();
        }catch(InterruptedException | BrokenBarrierException e){}
        //gameplay loop, repeatedly check if anyone won(not just if we won, but any of the other players too)
        while(won==-1){
            try {
                barrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
            }
            hand[4]=drawing_deck.getCard();
            
            fileoutput+="Player "+favourite+" draws "+hand[4]+" from deck "+favourite+"\n";
            winning=winningHand();
            if(hand[4]==favourite && won==-1){
                hand[4]=hand[completion];
                hand[completion]=favourite;
                completion++;
            }
            if(winning!=-1 || completion==4){   //check if current hand is a winning hand, cannot check at the end of the loop due to the slight chance of a winning hand appearing which is not witht the player's favourite cards
                won=favourite;
            }
            try {
                barrier.await();   //makes sure all players had a chance to update the "won" flag variable
            } catch (InterruptedException | BrokenBarrierException e) {
            }
            if(won!=-1){
                break;
            }
            Random random= new Random();
            int discarded_card_loc=random.nextInt(5-completion)+completion;
            int tempval=hand[4];
            hand[4]=hand[discarded_card_loc];
            hand[discarded_card_loc]=tempval;
            discarding_deck.addCard(hand[4], true);
            fileoutput+="Player "+favourite+" discards "+hand[4]+" to deck "+(favourite%threads +1)+"\n"+
            "Player "+favourite+" hand: "+hand[0]+" "+ hand[1]+" "+ hand[2]+" "+ hand[3]+"\n";
            try {
                //these barriers separate the process of drawing cards from the process of discarding them,
                //making sure no player can draw before everyone finished discarding and vice versa.
                barrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
            }

        }
        //end of gameplay loop
        //prepares player file and outputs deck file
        if(favourite==won){
            System.out.println("Player "+favourite+" wins");
            fileoutput+="Player "+favourite+" wins \nPlayer "+favourite+" exits \nPlayer "+favourite+
            " final hand: "+ winning+" "+ winning+" "+ winning+" "+ winning+"\n";
            for(int i=0; i<hand.length;i++){
                if(winning!=hand[i]){
                    discarding_deck.addCard(hand[i], true);
                    discarding_deck.writeDeckFile(favourite%threads+1);
                    break;
                }
            }

        }else{
            fileoutput+="Player "+won+" has informed Player "+favourite+" that Player "+won+" has won\n"+
            "Player "+favourite+" exits \nPlayer "+favourite+
            " final hand: "+ hand[0]+" "+ hand[1]+" "+ hand[2]+" "+ hand[3]+"\n";
            discarding_deck.addCard(hand[4], true);
            discarding_deck.writeDeckFile(favourite%threads+1);
        }
        //outputs Player file
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("Player"+favourite+"_output.txt"));
            writer.write(fileoutput);
            writer.close();
        } catch (IOException e) {
        }

    }

    public int winningHand(){ //checks if current hand(including 5th card, which is the card that has just been drawn) is winning.
        int[] unique= {-1,-1,-1};
        int counter=0;
        int[] appearances={1,1,1};
        for (int i = 0; i < hand.length && counter<3; i++) {
            boolean isunique=true;
            for(int j=0; j<counter;j++){
                if(unique[j]==hand[i]){
                    isunique=false;
                    appearances[j]++;
                }
            }
            if(isunique){
                unique[counter]=hand[i];
                counter++;
            }
        }
        for (int i = 0; i < appearances.length; i++) {
            if(appearances[i]==4){
                return unique[i];
            }
        }
        return -1;
    }
}