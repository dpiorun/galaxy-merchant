package main;

import java.util.HashMap;
import java.util.Map;

public class IntergalacticalParser {
    private final Map<String, Character> translationsMap;

    public IntergalacticalParser() {
        translationsMap = new HashMap<>();
    }

    public void set(String intergalacticalNumeral, Character romanNumeral) {
        translationsMap.put(intergalacticalNumeral, romanNumeral);
    }

    public int toTerrestrial(String intergalacticalNumeral) throws Exception {
        if (translationsMap.size() == 0)
            throw new Exception("Parser not set");

        String[] numerals = intergalacticalNumeral.split("\\s+");

        TerrestrialParser terrestrialParser = new TerrestrialParser();

        if (numerals.length == 1 && numerals[0].equals(""))
            return terrestrialParser.toArabic(numerals[0]);

        StringBuilder translated = new StringBuilder();
        for (String numeral : numerals)
            translated.append(translationsMap.get(numeral));

        return terrestrialParser.toArabic(translated.toString());
    }
}
