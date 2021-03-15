package Tail;

import java.io.*;
import java.util.ArrayDeque;


public class Tail {

    public enum Flag {
        LINES,
        CHARS
    }

    static Flag flag;
    static Integer number;

    public void fileCutter(String fileName, BufferedWriter writer) throws IOException {

        RandomAccessFile file = new RandomAccessFile(fileName, "r");
        long pointer;

        if (Tail.flag.equals(Flag.LINES)) {
            int lineFeed = 0;
            pointer = file.length();
            file.seek(pointer);

            while (pointer > -1 && lineFeed < Tail.number) {
                if (file.read() == '\n') lineFeed++;
                file.seek(pointer--);
            }

            if (pointer != -1) pointer += 3;
            else pointer += 1;
        }

        else {
            if (Tail.number < file.length()) {
                pointer = file.length() - Tail.number;
            } else pointer = 0;
        }

        file.seek(pointer);

        while (pointer < file.length()) {
            writer.write(file.read());
            pointer++;
        }

        writer.write("\n");
        file.close();

    }

    public void systemCutter(BufferedReader reader, BufferedWriter writer) throws IOException {

        ArrayDeque<String> input = new ArrayDeque<>();

        String line = reader.readLine();

        if (Tail.flag.equals(Flag.LINES)) {

            while(line != null) {
                input.add(line);
                line = reader.readLine();
                if (input.size() == Tail.number && line != null) input.pollFirst();
            }

            for (String each : input) writer.write(each + "\n");
        }

        else {

            while(line != null) {
                for (char symbol : line.toCharArray()) {
                    input.add(String.valueOf(symbol));
                    if (input.size() > Tail.number) input.pollFirst();
                }

                line = reader.readLine();
                if (line != null) input.add("\n");
                if (input.size() > Tail.number) input.pollFirst();
            }

            for (String each : input) writer.write(each);
            writer.write("\n");
        }

        reader.close();

    }
}