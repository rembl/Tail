package Tail;

import junit.framework.TestCase;
import org.apache.commons.io.FileUtils;

import java.io.*;

public class TailTest extends TestCase {

    public void testFileCutter1() throws IOException {

        String fileName = "files/test1";

        File expected1 = new File("files/equals1.1");
        File actual1 = File.createTempFile("tmp", "txt");
        BufferedWriter writer1 = new BufferedWriter(new FileWriter(actual1));
        Tail tail1 = new Tail();
        Tail.number = 3;
        Tail.flag = Tail.Flag.LINES;
        tail1.fileCutter(fileName, writer1);
        writer1.close();


        assertTrue(FileUtils.contentEqualsIgnoreEOL(expected1, actual1, null));
        actual1.deleteOnExit();
    }

    public void testFileCutter2() throws IOException {
        String fileName = "files/test1";

        File expected2 = new File("files/equals1.2");
        File actual2 = File.createTempFile("tmp", "txt");
        BufferedWriter writer2 = new BufferedWriter(new FileWriter(actual2));
        Tail tail2 = new Tail();
        Tail.number = 28;
        Tail.flag = Tail.Flag.CHARS;
        tail2.fileCutter(fileName, writer2);
        writer2.close();


        assertTrue(FileUtils.contentEqualsIgnoreEOL(expected2, actual2, null));
        actual2.deleteOnExit();

    }

    public void testSystemCutter1() throws IOException {
        String input = """
                hello
                goodbye
                apple
                orange
                milk
                cab
                cd

                hop hip hop
                """;

        BufferedReader reader1 = new BufferedReader(new StringReader(input));
        File expected1 = new File("files/equals1.1");
        File actual1 = File.createTempFile("tmp", "txt");
        BufferedWriter writer1 = new BufferedWriter(new FileWriter(actual1));
        Tail tail1 = new Tail();
        Tail.number = 3;
        Tail.flag = Tail.Flag.LINES;
        tail1.systemCutter(reader1, writer1);
        writer1.close();

        assertTrue(FileUtils.contentEqualsIgnoreEOL(expected1, actual1, null));
        actual1.deleteOnExit();
    }

    public void testSystemCutter2() throws IOException {
        String input = """
                hello
                goodbye
                apple
                orange
                milk
                cab
                cd

                hop hip hop
                """;

        BufferedReader reader2 = new BufferedReader(new StringReader(input));
        File expected2 = new File("files/equals1.2");
        File actual2 = File.createTempFile("tmp", "txt");
        BufferedWriter writer2 = new BufferedWriter(new FileWriter(actual2));
        Tail tail2 = new Tail();
        Tail.number = 28;
        Tail.flag = Tail.Flag.CHARS;
        tail2.systemCutter(reader2, writer2);
        writer2.close();

        assertTrue(FileUtils.contentEqualsIgnoreEOL(expected2, actual2, null));
        actual2.deleteOnExit();
    }
}