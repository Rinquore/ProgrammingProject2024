import org.junit.*;
import static org.junit.Assert.*;
import java.io.*;
import java.util.concurrent.CyclicBarrier;

public class PlayerTest {

    private Player player;
    private MockDeck drawingDeck;
    private MockDeck discardingDeck;

    private class MockDeck extends Deck {
        private int[] cards;
        private int index = 0;

        public MockDeck(int... cards) {
            this.cards = cards;
        }

        @Override
        public int getCard() {
            return cards[index++ % cards.length];
        }

    }

    @Before
    public void setUp() {
        drawingDeck = new MockDeck(5, 6, 7, 8);
        discardingDeck = new MockDeck();
        player = new Player(4, drawingDeck, discardingDeck);
        // player.hand = new int[]{4, 4, 6, 7, -1};
        player.barrier = new CyclicBarrier(1);
    }

    @Test
    public void testWinningHand_FourMatchingCards() {
        player.hand = new int[]{4, 4, -1, 4, 4};
        int winningCard = player.winningHand();
        assertEquals("The winning hand should contain 4 matching cards of 5", 4, winningCard);

        player.hand = new int[]{-1, 4, 4, 4, 4};
        int winningCard2 = player.winningHand();
        assertEquals("The winning hand should contain 4 matching cards of 5", 4, winningCard2);
    }

    @Test
    public void testWinningHand_NoWinningHand() {
        player.hand = new int[]{4, -4, 4, -4, -1};
        int winningCard = player.winningHand();
        assertEquals("There should be no winning hand", -1, winningCard);

    }

    @Test
    public void testFileOutput() throws IOException {
        // Use a mock deck that prevents `completion` from reaching 5
        drawingDeck = new MockDeck(1, 2, 3, 4, 5);
        discardingDeck = new MockDeck();
        player = new Player(4, drawingDeck, discardingDeck);
        player.barrier = new CyclicBarrier(1);

        String outputFile = "Player4_output.txt";

        player.start();
        try {
            player.join();
        } catch (InterruptedException e) {
            fail("Player thread interrupted");
        }

        String fileContents = readFile(outputFile);
        assertTrue("Output file should contain 'Player 4 wins' or similar content",
                fileContents.contains("Player 4") || fileContents.contains("wins"));
    }

    @Test
    public void testThreadingWithMultiplePlayers() {
        Player.threads = 3;
        Player.won = -1;
        Player.barrier = new CyclicBarrier(3);

        Player player1 = new Player(1, new MockDeck(1, 2, 3), discardingDeck);
        Player player2 = new Player(2, new MockDeck(2, 3, 4), discardingDeck);
        Player player3 = new Player(3, new MockDeck(3, 4, 5), discardingDeck);

        player1.start();
        player2.start();
        player3.start();

        try {
            player1.join();
            player2.join();
            player3.join();
        } catch (InterruptedException e) {
            fail("One of the threads was interrupted");
        }

        assertNotEquals("One of the players should have declared a win", -1, Player.won);
    }

    private String readFile(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        StringBuilder content = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            content.append(line).append("\n");
        }
        reader.close();
        return content.toString();
    }

}
