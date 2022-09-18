package test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MainTest {
    private final PrintStream standardOut = System.out;
    private final InputStream standardIn = System.in;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    private String ultimateStatement = "I have no idea what you are talking about";

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    private void provideUserInput(String data) {
        ByteArrayInputStream testIn = new ByteArrayInputStream(data.getBytes());
        System.setIn(testIn);
    }

    private Path getTestFilePath(String name) {
        return FileSystems.getDefault().getPath("src", "test", name);
    }

    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
        System.setIn(standardIn);
    }

    @Test
    void itPromptUserForFileSourceOrNotes() {
        String initialMessage = "Please provide a path to a file, by typing 'f:{path}' or provide a note in the console:";
        assertEquals(initialMessage, outputStreamCaptor.toString().trim());
    }

    @Test
    void itLoadsFileAndPassLineByLineToNotesScrapper() throws IOException {
        String userInput = "f:" + getTestFilePath("test-input.txt");
        provideUserInput(userInput);

        String expectedOutput = Files.readString(getTestFilePath("test-output.txt"), StandardCharsets.UTF_8);
        assertEquals(expectedOutput, outputStreamCaptor.toString().trim());
    }

    @Test
    void itTakesUsersInputAndPassToNotesScrapper() {
        provideUserInput("test");
        assertEquals(ultimateStatement, outputStreamCaptor.toString().trim());

        provideUserInput("second line");
        assertEquals(ultimateStatement, outputStreamCaptor.toString().trim());
    }

    @Test
    void itWaitsForMoreQuestionsAfterLoadingAFile() {
        String filePath = "f:" + getTestFilePath("test-input.txt");
        provideUserInput(filePath);
        provideUserInput("test");
        assertEquals(ultimateStatement, outputStreamCaptor.toString().trim());
    }
}