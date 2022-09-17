package test;

import main.IntergalacticalToTerrestrialParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class IntergalacticalToTerrestrialParserTest {
    IntergalacticalToTerrestrialParser parser;

    @BeforeEach
    public void setup() {
        parser = new IntergalacticalToTerrestrialParser();
    }

    @Test
    void itShouldBePossibleToSetParser() {
        parser.set("glob", "I");
        parser.set("prok", "V");
        parser.set("pish", "X");
        parser.set("tegj", "L");

        Assertions.assertEquals(parser.toTerrestrial("pish tegj glob glob"), 42);
        Assertions.assertEquals(parser.toTerrestrial("pish pish pish prok glob"), 36);

        parser.set("foo", "C");
        parser.set("bar", "D");
        parser.set("baz", "M");

        Assertions.assertEquals(parser.toTerrestrial("baz bar pish foo prok glob"), 1596);
        Assertions.assertEquals(parser.toTerrestrial("baz prok"), 1005);
    }

    @Test
    void itShouldThrowIfTheProvidedNumeralIsNotValid() {
        parser.set("glob", "I");
        parser.set("prok", "V");
        parser.set("pish", "X");
        parser.set("tegj", "L");

        Exception exception = assertThrows(Exception.class, () -> parser.toTerrestrial("TEST"));
        assertEquals("/ invalid numeral", exception.getMessage());

        exception = assertThrows(Exception.class, () -> parser.toTerrestrial("pish pish pish pish"));
        assertEquals("/ invalid numeral", exception.getMessage());
    }
}