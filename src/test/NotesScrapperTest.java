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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

class NotesScrapperTest {
    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    private IntergalacticalParser parser;
    private NotesScrapper scrapper;


    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
        parser = new IntergalacticalParser();
        scrapper = new NotesScrapper(parser);
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
        IntergalacticalParser spy = spy(parser);
        scrapper.scrap(note);
        String[] values = note.split("\\s+is\\s+");
        verify(spy).set(values[0], values[1].charAt(0));
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
        String[] secondPart = values[1].split("\\s+");
        String productName = firstPart[firstPart.length - 1];
        String price = secondPart[0];
        Product productMock = spy(new Product(productName));
        scrapper.scrap(note);
        verify(productMock).setUnitPrice(Integer.parseInt(price));
    }

    @ParameterizedTest
    @CsvSource({
            "how much is pish tegj glob glob, pish tegj glob glob is 42",
    })
    void itTranslatesIntergalacticalNumerals(String note, String answer) {
        scrapper.scrap(note);
        assertEquals(answer, outputStreamCaptor.toString().trim());
    }

    @ParameterizedTest
    @CsvSource({
            "how many Credits is glob prok Silver ?,    glob prok Silver is 68 Credits",
            "  how many Credits is glob prok Gold ?,    glob prok Gold is 57800 Credits",
            "how many Credits is glob prok Iron ?,      glob prok Iron is 782 Credits",
    })
    void itCalculatesThePriceOfAQuery(String note, String answer) {
        scrapper.scrap(note);
        assertEquals(answer, outputStreamCaptor.toString().trim());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "how much wood could a woodchuck chuck if a woodchuck could chuck wood ?"
    })
    void itAdmitsItsIgnoranceWhenApplicable(String note) {
        scrapper.scrap(note);
        assertEquals("I have no idea what you are talking about", outputStreamCaptor.toString().trim());
    }
}