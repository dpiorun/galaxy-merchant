package main;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NotesScrapper {
    private IntergalacticalParser parser;
    private HashMap<String, Product> products;
    private String ultimateStatement = "I have no idea what you are talking about";
    private String note;

    public NotesScrapper(IntergalacticalParser parser, HashMap<String, Product> productStore) {
        products = productStore;
        this.parser = parser;
    }

    public void scrap(String note) {
        this.note = note.trim().replaceAll("\\s+", " ");
        boolean itIsAQuestion = this.note.matches(".+\\?$");
        if (itIsAQuestion) this.handleQuestion();
        else this.handleData();
    }

    private Matcher createMatcher(String regex) {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(this.note);
    }

    private void handleData() {
        // ex. match: "glob is I"
        Matcher matcher = createMatcher("(?<intergalacticalNumeral>\\w+)(\\s+is\\s+)(?<romanNumeral>[MDCLXVI]$)");
        if (matcher.matches()) {
            String intergalacticalNumeral = matcher.group("intergalacticalNumeral");
            String romanNumeral = matcher.group("romanNumeral");

            this.parser.set(intergalacticalNumeral, romanNumeral.charAt(0));
            return;
        }

        // ex. match "glob glob Silver is 34 Credits"
        matcher = createMatcher("(?<numeral>([a-zA-Z]+\\s*?\\b)*)(\\s+)(?<product>\\w+)(\\s+is\\s+)(?<price>[0-9]+)(\\s+Credits)");
        if (matcher.matches()) {
            String name = matcher.group("product");
            String numeral = matcher.group("numeral");
            double price = Double.parseDouble(matcher.group("price"));

            try {
                double unitPrice = price / parser.toTerrestrial(numeral);
                Product product = new Product(name);
                product.setUnitPrice(unitPrice);
                products.put(name, product);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            return;
        }

        System.out.println(ultimateStatement);
    }

    private void handleQuestion() {
        // ex. match "how much is pish tegj glob glob ?"
        Matcher matcher = createMatcher("(how\\s+much\\s+is\\s+)(?<numeral>([a-zA-Z]+\\s*?\\b)*)(\\s*?\\?$)");
        if (matcher.matches()) {
            try {
                String intergalacticalNumeral = matcher.group("numeral");
                int parsedNumeral = parser.toTerrestrial(intergalacticalNumeral);

                System.out.println(intergalacticalNumeral + " is " + parsedNumeral);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            return;
        }

        // ex. match "how many Credits is pish tegj glob glob Silver ?"
        matcher = createMatcher("(how\\s+many\\s+Credits\\s+is\\s+)(?<numeral>([a-zA-Z]+\\s+?\\b)*)(?<product>[a-zA-Z]+)(\\s*?\\?$)");
        if (matcher.matches()) {
            try {
                String numeral = matcher.group("numeral").trim();
                int quantity = parser.toTerrestrial(numeral);

                String productName = matcher.group("product");
                Product queriedProduct = products.get(productName);
                Double price = queriedProduct.getUnitPrice() * quantity;

                System.out.printf(
                        "%s %s is %s Credits%n",
                        numeral,
                        productName,
                        Utils.formatPrice(price)
                );
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            return;
        }

        System.out.println(ultimateStatement);
    }
}
