package Tail;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Writer;
import java.util.LinkedList;

public class Tail {

    static String flag;
    static Integer number;

    public static void cutter(BufferedReader reader, Writer writer) throws IOException {

        if (flag.equals("lines")) {
            LinkedList<String> lines = new LinkedList<>();
            String line = reader.readLine();
            while (!line.isEmpty()) {
                if (lines.size() == number) lines.pollFirst();
                lines.add(line);
                line = reader.readLine();
            }

            for (String each : lines) {
                writer.write(each + "\n");
            }

            writer.flush();
        }

        else {
            LinkedList<String> chars = new LinkedList<>();
            String character = String.valueOf(reader.read());
            while (!character.isEmpty()) {
                if (chars.size() == number) chars.pollFirst();
                chars.add(character);
                character = String.valueOf(reader.read());
            }

            for (String each : chars) {
                writer.write(each);
            }

            writer.flush();
        }
    }

    public static void main(String[] args) {
        new TailLauncher().launch(args);
    }
}