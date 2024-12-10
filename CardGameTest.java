import org.junit.*;
import java.io.*;
import static org.junit.Assert.*;

public class CardGameTest {

    @Test
    public void testValidInputs() throws IOException {
        String fileName = "validPack.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write("1\n1\n1\n1\n");
            writer.write("2\n2\n2\n2\n");
            writer.write("3\n3\n3\n3\n");
            writer.write("4\n4\n4\n4\n4\n4\n4\n4\n4\n4\n4\n4");

        }

        String[] args = {"3", fileName};
        int result = main.validInputs(args[0], args[1]);

        assertEquals(3, result);
    }

    @Test
    public void testValidGameSimulation() throws IOException {
        String[] args = {"3", "validPack.txt"};

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("validPack.txt"))) {
            writer.write("1\n1\n1\n1\n");
            writer.write("2\n2\n2\n2\n");
            writer.write("3\n3\n3\n3\n");
            writer.write("4\n4\n4\n4\n4\n4\n4\n4\n4\n4\n4\n4");
        }

        main.main(args);

        boolean winnerDeclared = false;
        for (int i = 1; i <= 3; i++) {
            File playerOutput = new File("Player" + i + "_output.txt");
            assertTrue("Player " + i + " output file should exist", playerOutput.exists());
            String playerContents = readFile("Player" + i + "_output.txt");
            if (playerContents.contains("wins")) {
                winnerDeclared = true;
            }
        }
        assertTrue("At least one player should declare a win", winnerDeclared);

        for (int i = 1; i <= 3; i++) {
            File deckOutput = new File("Deck" + i + "_output.txt");
            assertTrue("Deck " + i + " output file should exist", deckOutput.exists());
            String deckContents = readFile("Deck" + i + "_output.txt");
            assertTrue("Deck " + i + " should have contents written", deckContents.contains("deck" + i + " contents"));
        }
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
