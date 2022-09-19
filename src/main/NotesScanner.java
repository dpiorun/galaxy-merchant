package main;

import java.util.HashMap;
import java.util.Scanner;

public class NotesScanner {
    private Scanner in;
    private NotesScrapper scrapper;

    public NotesScanner() {
        in = new Scanner(System.in);
        IntergalacticalParser parser = new IntergalacticalParser();
        HashMap<String, Product> products = new HashMap<>();
        scrapper = new NotesScrapper(parser, products);
    }

    public void scan() {
        System.out.println("Please provide a path to a file, by typing 'f:{path}' or provide a note in the console:");

        String input = in.nextLine();

        System.out.println(input);
    }


}
