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
        long pointer;

        if (tail.flag.equals(Flag.LINES)) {
            int lineFeed = 0;
            pointer = file.length();
            file.seek(pointer);

            while (pointer > -1 && lineFeed < tail.number) {
                if (file.read() == '\n') lineFeed++;
                file.seek(pointer--);
            }

            if (pointer != -1) pointer += 3;
            else pointer += 1;
            file.seek(pointer);

            if (lineFeed < tail.number - 1) {
                System.out.println("Error: file not long enough");
                return;
            }
        }

        else {
            if (tail.number > file.length()) {
                System.out.println("Error: file not long enough");
                return;
            }

            else {
                pointer = file.length() - tail.number;
                file.seek(pointer);
            }
        }

        while (pointer < file.length()) {
            writer.write(file.read());
            pointer++;
        }

        writer.write("\n");
        file.close();

    }

    public static void systemCutter(String terminator, BufferedReader reader, BufferedWriter writer, Tail Tail) throws IOException {

        ArrayDeque<String> input = new ArrayDeque<>();

        String line = reader.readLine();

        if (Tail.flag.equals(Flag.LINES)) {

            while(!line.equals(terminator)) {
                input.add(line);
                line = reader.readLine();
                if (input.size() == Tail.number && !line.equals(terminator)) input.pollFirst();
            }

            if (input.size() < Tail.number) System.out.println("Error: file not long enough");
            else for (String each : input) writer.write(each + "\n");
        }

        else {

            while(!line.equals(terminator)) {
                for (char symbol : line.toCharArray()) {
                    input.add(String.valueOf(symbol));
                    if (input.size() > Tail.number) input.pollFirst();
                }

                line = reader.readLine();
                if (!line.equals(terminator)) input.add("\n");
                if (input.size() > Tail.number) input.pollFirst();
            }

            if (input.size() < Tail.number) System.out.println("Error: file not long enough");
            else for (String each : input) writer.write(each);
            writer.write("\n");
        }

        reader.close();

    }
}