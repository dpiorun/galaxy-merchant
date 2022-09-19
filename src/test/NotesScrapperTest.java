package test;

import main.IntergalacticalParser;
import main.NotesScrapper;
import main.Product;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

class NotesScrapperTest {
    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    private IntergalacticalParser parser;
    private HashMap<String, Product> products;
    private NotesScrapper scrapper;

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
        parser = spy(new IntergalacticalParser());
        products = new HashMap();
        scrapper = new NotesScrapper(parser, products);
    }

    private void setupParser() {
        parser.set("glob", 'I');
        parser.set("prok", 'V');
        parser.set("pish", 'X');
        parser.set("tegj", 'L');
    }

    private void setupProduct() {
        products.put("Test", new Product("Test"));
        products.get("Test").setUnitPrice(10.50);
    }

    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "glob is I",
            "prok is V",
            "  pish is   X  ",
            "tegj is L",
    })
    void itAddsTranslationsToIntergalacticalParser(String note) {
        note = note.trim();
        scrapper.scrap(note);
        String[] values = note.split("\\s+is\\s+");
        verify(parser).set(values[0], values[1].charAt(0));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "glob glob Silver is 34 Credits",
            "glob prok Gold is 57800 Credits",
            "  pish pish Iron is  3910   Credits",
    })
    void itAddsProductAndItsPriceFromANote(String note) {
        note = note.trim();
        String[] values = note.split("\\s+is\\s+");
        String[] firstPart = values[0].split("\\s+");
        String productName = firstPart[firstPart.length - 1];
        setupParser();
        scrapper.scrap(note);

        assertTrue(products.containsKey(productName));
    }

    @ParameterizedTest
    @CsvSource({
            "how much is prok ?,                        prok is 5",
            "how  much  is  pish  tegj  glob glob?,     pish tegj glob glob is 42",
    })
    void itTranslatesIntergalacticalNumerals(String note, String answer) {
        setupParser();

        scrapper.scrap(note);
        assertEquals(answer, outputStreamCaptor.toString().trim());
    }

    @ParameterizedTest
    @CsvSource({
            "how many Credits is glob prok Test ?,    glob prok Test is 42 Credits",
            "  how  many  Credits  is  prok  Test?,   prok Test is 52.5 Credits",
    })
    void itCalculatesThePriceOfAQuery(String note, String answer) {
        setupParser();
        setupProduct();

        scrapper.scrap(note);
        assertEquals(answer, outputStreamCaptor.toString().trim());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "how much wood could a woodchuck chuck if a woodchuck could chuck wood ?"
    })
    void itAdmitsItsIgnoranceWhenApplicable(String note) {
        setupParser();

        scrapper.scrap(note);
        assertEquals("I have no idea what you are talking about", outputStreamCaptor.toString().trim());
    }
}