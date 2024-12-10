import org.junit.*;
import static org.junit.Assert.assertEquals;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import static org.junit.Assert.fail;


public class DeckTest {
    private Deck deck;

    @Before
    public void setUp() {
        deck = new Deck(); // Initialize a new deck for each test
    }

    @Test
    public void testAddCard() {
        deck.AddCard(5, true);
        deck.AddCard(10, true);

        assertEquals(5, deck.getCard());
    }

    @Test
    public void testGetCard() {
        deck.AddCard(10, false);
        deck.AddCard(5, false);

        int topCard = deck.getCard();
        assertEquals(5, topCard);
    }

    @Test
    public void testWriteDeckFile() {
        deck.AddCard(5, false);
        deck.AddCard(10, false);

        deck.writeDeckFile(1, 99);
        File outputFile = new File("Deck1_output.txt");
        try {
            BufferedReader reader = new BufferedReader(new FileReader(outputFile));
            StringBuilder fileContents = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                fileContents.append(line).append("\n");
            }
            reader.close();

            String expectedContent = "deck1 contents: 99 5 10\n";
            assertEquals(expectedContent, fileContents.toString());

        } catch (IOException e) {

        }

    }
    @Test
    public void testGetCard_EmptyDeck_ThrowsException() {
        Deck deck = new Deck();

        try {
            deck.getCard();
            fail("Expected IndexOutOfBoundsException to be thrown");
        } catch (IndexOutOfBoundsException e) {

        }
    }
}
