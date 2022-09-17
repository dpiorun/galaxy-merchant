package test;

import main.RomanToArabicParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RomanToArabicParserTest {
    RomanToArabicParser parser;

    @BeforeEach
    void setUp() {
        parser = new RomanToArabicParser();
    }

    @Test
    void itShouldParseRomanToArabic() throws Exception {
        assertEquals(parser.toArabic("XL"), 40);
        assertEquals(parser.toArabic("XLII"), 42);
        assertEquals(parser.toArabic("XIX"), 19);
        assertEquals(parser.toArabic("XVI"), 16);
        assertEquals(parser.toArabic("MV"), 1005);
        assertEquals(parser.toArabic("MDXCVI"), 1596);
    }

    @Test
    void itShouldThrowIfProvidedDataIsNotARomanNumeral() {
        Exception exception = assertThrows(Exception.class, () -> parser.toArabic("TEST"));
        assertEquals("invalid numeral", exception.getMessage());
    }

    @Test
    void itShouldThrowIfProvidedDataIsNotAValidRomanNumeral() {
        Exception exception = assertThrows(Exception.class, () -> parser.toArabic("XXXX"));
        assertEquals("invalid numeral", exception.getMessage());

        exception = assertThrows(Exception.class, () -> parser.toArabic("MDMD"));
        assertEquals("invalid numeral", exception.getMessage());
    }
}