package Tail;

import java.io.*;
import java.util.ArrayDeque;


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

        if (Tail.flag.equals(Flag.LINES)) {
            ArrayDeque<String> input = new ArrayDeque<>();
            while(true) {
                String line = reader.readLine();
                if (line.isEmpty()) break;
                if (input.size() == Tail.number) input.pollFirst();
                input.add(line);
            }

            for (String each : input) writer.write(each);
        }

        else {
            StringBuilder input = new StringBuilder();
            while(true) {
                String line = reader.readLine();
                if (line.isEmpty()) break;
                input.append(line);
                if (input.length() >= Tail.number) input.replace(0, input.length() - Tail.number, "");
            }

            writer.write(String.valueOf(input));

        }

        reader.close();
        writer.close();

    }
}