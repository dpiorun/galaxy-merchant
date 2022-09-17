package test;

import main.TerrestrialParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TerrestrialParserTest {
    TerrestrialParser parser;

    @BeforeEach
    void setUp() {
        parser = new TerrestrialParser();
    }

    @ParameterizedTest
    @CsvSource({
            "XL,        40",
            "XLII,      42",
            "XIX,       19",
            "XVI,       16",
            "MV,        1005",
            "MDXCVI,    1596"
    })
    void itShouldParseRomanToArabic(String romanNumeral, int number) throws Exception {
        assertEquals(parser.toArabic(romanNumeral), number);
    }

    @Test
    void itShouldThrowIfProvidedDataIsNotARomanNumeral() {
        Exception exception = assertThrows(Exception.class, () -> parser.toArabic("TEST"));
        assertEquals("Invalid numeral", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"XXXX", "MDMD"})
    void itShouldThrowIfProvidedDataIsNotAValidRomanNumeral(String romanNumeral) {
        Exception exception = assertThrows(Exception.class, () -> parser.toArabic(romanNumeral));
        assertEquals("Invalid numeral", exception.getMessage());
    }

    @Test
    void itShouldHandleNoneNumeral() throws Exception {
        assertEquals(parser.toArabic(""), 0);
    }
}