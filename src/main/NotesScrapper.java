package main;

public class NotesScrapper {
    IntergalacticalParser parser;

    public NotesScrapper(IntergalacticalParser parser) {
        this.parser = parser;
    }

    public void scrap(String note) {
        note = note.trim().toLowerCase();
        if (note.contains("how much is")) {
            System.out.println();
        }

    }

}
