package Tail;

import junit.framework.TestCase;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class TailTest extends TestCase {

    public void testFileCutter() throws IOException {

        String fileName = "files/test1";

        File expected1 = new File("files/equals1.1");
        File actual1 = File.createTempFile("tmp", "txt");
        BufferedWriter writer1 = new BufferedWriter(new FileWriter(actual1));
        Tail tail1 = new Tail();
        tail1.number = 3;
        tail1.flag = Tail.Flag.LINES;
        Tail.fileCutter(fileName, writer1, tail1);
        writer1.close();

        assertEquals(FileUtils.readLines(expected1, StandardCharsets.UTF_8), FileUtils.readLines(actual1,  StandardCharsets.UTF_8));
        actual1.deleteOnExit();

        File expected2 = new File("files/equals1.2");
        File actual2 = File.createTempFile("tmp", "txt");
        BufferedWriter writer2 = new BufferedWriter(new FileWriter(actual2));
        Tail tail2 = new Tail();
        tail2.number = 28;
        tail2.flag = Tail.Flag.CHARS;
        Tail.fileCutter(fileName, writer2, tail2);
        writer2.close();

        assertEquals(FileUtils.readLines(expected2, StandardCharsets.UTF_8), FileUtils.readLines(actual2,  StandardCharsets.UTF_8));
        actual1.deleteOnExit();
    }

    public void testSystemCutter() throws IOException {

        String input = """
                hello
                goodbye
                apple
                orange
                milk
                ab
                cd

                hop hip hop
                end
                """;
        String terminator = "end";

        BufferedReader reader1 = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8))));
        File expected1 = new File("files/equals1.1");
        File actual1 = File.createTempFile("tmp", "txt");
        BufferedWriter writer1 = new BufferedWriter(new FileWriter(actual1));
        Tail tail1 = new Tail();
        tail1.number = 3;
        tail1.flag = Tail.Flag.LINES;
        Tail.systemCutter(terminator, reader1, writer1, tail1);
        writer1.close();

        assertEquals(FileUtils.readLines(expected1, StandardCharsets.UTF_8), FileUtils.readLines(actual1,  StandardCharsets.UTF_8));
        actual1.deleteOnExit();

        BufferedReader reader2 = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8))));
        File expected2 = new File("files/equals1.2");
        File actual2 = File.createTempFile("tmp", "txt");
        BufferedWriter writer2 = new BufferedWriter(new FileWriter(actual2));
        Tail tail2 = new Tail();
        tail2.number = 28;
        tail2.flag = Tail.Flag.CHARS;
        Tail.systemCutter(terminator,reader2, writer2, tail2);
        writer2.close();

        assertEquals(FileUtils.readLines(expected2, StandardCharsets.UTF_8), FileUtils.readLines(actual2,  StandardCharsets.UTF_8));
        actual1.deleteOnExit();
    }
}