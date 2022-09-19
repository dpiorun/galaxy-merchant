package galaxy;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UtilsTest {
    @ParameterizedTest
    @CsvSource({
            "232.00000000, 232",
            "0.18000000000, 0.18",
            "1237875192.0, 1237875192",
            "4.5800000000, 4.58",
            "0.00000000, 0",
            "1.23450000, 1.2345",
    })
    void itFormatsDoubleProperly(Double input, String output) {
        assertEquals(Utils.formatPrice(input), output);
    }

    @ParameterizedTest
    @NullSource
    void itHandlesNullValues(Double input) {
        assertEquals(Utils.formatPrice(input), "null");
    }
}