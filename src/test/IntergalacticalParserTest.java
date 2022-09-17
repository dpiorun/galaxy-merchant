package test;

import main.IntergalacticalParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class IntergalacticalParserTest {
    IntergalacticalParser parser;

    private void parserSetup() {
        parser.set("glob", 'I');
        parser.set("prok", 'V');
        parser.set("pish", 'X');
        parser.set("tegj", 'L');
    }

    @BeforeEach
    public void setup() {
        parser = new IntergalacticalParser();
    }

    @ParameterizedTest
    @CsvSource({
            "pish tegj glob glob,       42",
            "pish pish pish prok glob,  36"
    })
    void itShouldParseIntergalacticalNumerals(String intergalacticalNumeral, int number) throws Exception {
        parserSetup();
        Assertions.assertEquals(parser.toTerrestrial(intergalacticalNumeral), number);
    }

    @ParameterizedTest
    @CsvSource({
            "baz bar pish foo prok glob,    1596",
            "baz prok,                      1005"
    })
    void itShouldWorkWithAllRomanNumerals(String intergalacticalNumeral, int number) throws Exception {
        parserSetup();
        parser.set("foo", 'C');
        parser.set("bar", 'D');
        parser.set("baz", 'M');

        Assertions.assertEquals(parser.toTerrestrial(intergalacticalNumeral), number);
    }

    @ParameterizedTest
    @ValueSource(strings = {"TEST", "pish pish pish pish"})
    void itShouldThrowIfTheProvidedNumeralIsNotValid(String testString) {
        parserSetup();
        Exception exception = assertThrows(Exception.class, () -> parser.toTerrestrial(testString));
        assertEquals("Invalid numeral", exception.getMessage());
    }

    @Test
    void itShouldHandleNoneNumeral() throws Exception {
        parserSetup();
        Assertions.assertEquals(parser.toTerrestrial(""), 0);
    }

    @Test
    void itShouldHandleMultipleWhitespacesBetweenIntergalacticalNumerals() throws Exception {
        parserSetup();
        Assertions.assertEquals(parser.toTerrestrial("pish    pish  pish prok  glob"), 36);
    }

    @Test
    void itShouldThrowIfTheParserWasNotSet() {
        Exception exception = assertThrows(Exception.class, () -> parser.toTerrestrial("test"));
        assertEquals("Parser not set", exception.getMessage());
    }
}