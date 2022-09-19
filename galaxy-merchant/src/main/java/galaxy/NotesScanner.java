package galaxy;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NotesScanner {
    private final Scanner in;
    private final NotesScrapper scrapper;

    public NotesScanner() {
        in = new Scanner(System.in);
        IntergalacticalParser parser = new IntergalacticalParser();
        HashMap<String, Product> products = new HashMap<>();
        scrapper = new NotesScrapper(parser, products);
    }

    public void scan() {
        System.out.println("Please provide a path to a file, by typing 'f:{path}' or provide a note in the console:");

        while (in.hasNextLine()) {
            String userInput = in.nextLine();
            if (userInput.equals("exit"))
                System.exit(0);

            Pattern pattern = Pattern.compile("(^f:)(?<filePath>.+)");
            Matcher matcher = pattern.matcher(userInput);
            if (matcher.matches()) {
                String filePath = matcher.group("filePath");
                try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
                    String note;
                    while ((note = br.readLine()) != null) {
                        scrapper.scrap(note);
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            } else {
                scrapper.scrap(userInput);
            }
        }
    }
}
