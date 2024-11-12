import java.util.concurrent.CyclicBarrier;

public class Player extends Thread {
    //public static CyclicBarrier barrier;
    private int favourite;
    private String output_string="";
    public static int threads=0;
    public static volatile int won=-1;

    //make constructor

    @Override
    public void run(){
        //add initial hand to output_string
        //check if anyone's initial hand is winning
        //sort hand
        //loop(draw card->check for winner|->discard card|)
        //use barriers to sync threads

        //output files

    }

    //other functions

}
