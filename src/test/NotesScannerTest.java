package test;

import main.NotesScanner;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

import static com.github.stefanbirkner.systemlambda.SystemLambda.withTextFromSystemIn;
import static org.junit.jupiter.api.Assertions.assertEquals;

class NotesScannerTest {
    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    private final String ultimateStatement = "I have no idea what you are talking about";
    private final String initialMessage = "Please provide a path to a file, by typing 'f:{path}' or provide a note in the console:";


    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    private Path getTestFilePath(String name) {
        return FileSystems.getDefault().getPath("src", "test", name);
    }

    @AfterEach
    void tearDown() {

        System.setOut(standardOut);
    }

    @Test
    void itPromptUserForFileSourceOrNotes() throws Exception {
        withTextFromSystemIn().execute(() -> {
            NotesScanner scanner = new NotesScanner();
            scanner.scan();
            assertEquals(initialMessage, outputStreamCaptor.toString().trim());
        });
    }

    @Test
    void itLoadsFileAndPassLineByLineToNotesScrapper() throws Exception {
        String userInput = "f:" + getTestFilePath("test-input.txt");

        withTextFromSystemIn(userInput).execute(() -> {
            NotesScanner scanner = new NotesScanner();
            scanner.scan();
            String expectedOutput = Files.readString(getTestFilePath("test-output.txt"), StandardCharsets.UTF_8);
            assertEquals(
                    String.format(
                            "%s%s%s",
                            initialMessage,
                            System.lineSeparator(),
                            expectedOutput
                    ),
                    outputStreamCaptor.toString().trim()
            );
        });
    }

    @Test
    void itTakesUsersInputAndPassToNotesScrapper() throws Exception {
        withTextFromSystemIn("test", "second line").execute(() -> {
            NotesScanner scanner = new NotesScanner();
            scanner.scan();
            assertEquals(
                    String.format(
                            "%s%s%s%s%s",
                            initialMessage,
                            System.lineSeparator(),
                            ultimateStatement,
                            System.lineSeparator(),
                            ultimateStatement
                    ),
                    outputStreamCaptor.toString().trim()
            );
        });
    }

    @Test
    void itWaitsForMoreQuestionsAfterLoadingAFile() throws Exception {
        String filePath = "f:" + getTestFilePath("test-input.txt");
        withTextFromSystemIn(filePath, "test").execute(() -> {
            NotesScanner scanner = new NotesScanner();
            scanner.scan();
            String expectedFileOutput = Files.readString(getTestFilePath("test-output.txt"), StandardCharsets.UTF_8);
            assertEquals(
                    String.format(
                            "%s%s%s%s%s",
                            initialMessage,
                            System.lineSeparator(),
                            expectedFileOutput,
                            System.lineSeparator(),
                            ultimateStatement
                    ),
                    outputStreamCaptor.toString().trim());
        });
    }
}