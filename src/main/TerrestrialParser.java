package main;

import java.util.HashMap;
import java.util.Map;

import static java.util.regex.Pattern.matches;

public class TerrestrialParser {
    private final Map<Character, Integer> numbersMap;

    public TerrestrialParser() {
        numbersMap = new HashMap<>();
        numbersMap.put('I', 1);
        numbersMap.put('V', 5);
        numbersMap.put('X', 10);
        numbersMap.put('L', 50);
        numbersMap.put('C', 100);
        numbersMap.put('D', 500);
        numbersMap.put('M', 1000);
    }

    private static boolean validateRomanNumeral(String input) {
        // Regex based on https://stackoverflow.com/questions/267399/how-do-you-match-only-valid-roman-numerals-with-a-regular-expression
        return matches("^M{0,4}(CM|CD|D?C{0,3})(XC|XL|L?X{0,3})(IX|IV|V?I{0,3})$", input);
    }

    public int toArabic(String romanNumeral) throws Exception {
        if (!validateRomanNumeral(romanNumeral)) throw new Exception("Invalid numeral");

        int result = 0;
        for (int i = 0; i < romanNumeral.length(); i++) {
            char ch = romanNumeral.charAt(i);

            if (i > 0 && numbersMap.get(ch) > numbersMap.get(romanNumeral.charAt(i - 1)))
                result += numbersMap.get(ch) - 2 * numbersMap.get(romanNumeral.charAt(i - 1));
            else result += numbersMap.get(ch);
        }
        return result;
    }
}
