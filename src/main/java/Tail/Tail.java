package Tail;

import java.io.*;
import org.apache.commons.io.input.ReversedLinesFileReader;

public class Tail {

    public enum Flag {
        LINES,
        CHARS
    }

    Flag flag;
    Integer number;

    public static void fileCutter(String fileName, BufferedWriter writer, Tail Tail) throws IOException {

        RandomAccessFile file = new RandomAccessFile(fileName, "r");

        if (Tail.flag.equals(Flag.LINES)) {
            long pointer = file.length() - 1;
            int lineFeed = 0;
            while (pointer > 0 && lineFeed < Tail.number) {
                file.seek(pointer--);
                if (file.read() == 10) lineFeed++;
            }
            while (pointer <= file.length()){
                writer.write(file.read());
                pointer++;
            }
        }

        else {
            long pointer = file.length();
            int chars = 1;
            while (pointer > 0 && chars < Tail.number) {
                file.seek(pointer--);
                chars++;
            }
            while (pointer <= file.length()){
                writer.write(file.read());
                pointer++;
            }
        }
    }

    public static void systemCutter(BufferedReader reader, BufferedWriter writer) throws IOException {

    }
}