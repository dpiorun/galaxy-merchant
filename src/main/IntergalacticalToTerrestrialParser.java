package main;

import java.util.HashMap;
import java.util.Map;

public class IntergalacticalToTerrestrialParser {
    private Map<String, Character> translationsMap;

    public IntergalacticalToTerrestrialParser() {
        translationsMap = new HashMap<>();
    }

    public void set(String intergalacticalNumeral, Character romanNumeral) {
        translationsMap.put(intergalacticalNumeral, romanNumeral);
    }

    public int toTerrestrial(String intergalacticalNumeral) throws Exception {
        if (translationsMap.size() == 0) throw new Exception("parser not set");

        String[] numerals = intergalacticalNumeral.split("\\s+");

        RomanToArabicParser terrestrialParser = new RomanToArabicParser();
        String translated = "";
        for (String numeral : numerals)
            translated += translationsMap.get(numeral);

        return terrestrialParser.toArabic(translated);
    }
}
