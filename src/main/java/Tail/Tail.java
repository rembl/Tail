package Tail;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Tail {

    public enum Flag {
        LINES,
        CHARS
    }

    Flag flag;
    Integer number;

    public static void fileCutter(String fileName, BufferedWriter writer, Tail tail) throws IOException {

        RandomAccessFile file = new RandomAccessFile(fileName, "r");

        if (tail.flag.equals(Flag.LINES)) {
            long pointer = file.length() - 1;
            int lineFeed = 0;
            while (pointer > 0 && lineFeed < tail.number) {
                file.seek(pointer--);
                if (file.read() == '\n') lineFeed++;
            }
            while (pointer <= file.length()){
                writer.write(file.read());
                pointer++;
            }

        }

        else {
            for (long pointer = file.length() - tail.number + 1; pointer <= file.length(); pointer++) {
                file.seek(pointer);
                writer.write(file.read());
            }

        }

        file.close();
        writer.close();

    }

    public static void systemCutter(BufferedReader reader, BufferedWriter writer, Tail Tail) throws IOException {

        List<String> input = new ArrayList<>();
        while(true) {
            String line = reader.readLine();
            if (line.equals("")) break;
            input.add(line);
        }

        reader.close();

        if (Tail.flag.equals(Flag.LINES)) {
            while (Tail.number >= 0) {
                writer.write(input.get(input.size() - Tail.number));
                Tail.number--;
            }
        }

        else {
            StringBuilder string = new StringBuilder();
            int index = input.size() - 1;
            do {
                string.insert(0, input.get(index));
                index--;
            } while (string.length() < Tail.number);
            writer.write(String.valueOf(string.replace(0, string.length() - Tail.number, "")));
        }

        writer.close();

    }
}