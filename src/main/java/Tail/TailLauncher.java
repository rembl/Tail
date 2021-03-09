package Tail;

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TailLauncher {

    @Option(name = "-o", metaVar = "OutputName", usage = "File output name")
    private String outputName = "";

    @Option(name = "-c", metaVar = "LastCharacters", usage = "Last num characters")
    private Integer c;

    @Option(name = "-n", metaVar = "LastLines", usage = "Last num lines")
    private Integer n;

    @Argument(metaVar = "InputFiles", usage = "Files input names")
    private List<String> inputFiles = new ArrayList<>();

    public void launch(String[] args) {

        CmdLineParser parser = new CmdLineParser(this);

        try {
            parser.parseArgument(args);
        } catch (CmdLineException e) {
            System.err.println(e.getMessage());
            System.err.println("tail [-c num|-n num] [-o ofile] file0 file1 file2 …\n");
            parser.printUsage(System.err);
            return;
        }

        try {
            BufferedWriter writer;
            if (outputName.equals("")) {
                writer = new BufferedWriter(new OutputStreamWriter(System.out));
            } else writer = new BufferedWriter(new FileWriter(outputName));

            if (inputFiles.isEmpty()) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                hopper(reader, writer);
            }

            for (String file : inputFiles) {
                writer.write(file + "\n");
                hopper(new BufferedReader(new FileReader(file)), writer);
                writer.write("\n");
            }

            writer.close();

        } catch (IOException e) {
            System.err.println(e.getMessage());
            return;
        }
    }

    public void hopper(BufferedReader reader, BufferedWriter writer) throws IOException {
        if (c == null && n == null) {
            Tail.flag = "lines";
            Tail.number = 10;
        }

        if (c != null && n != null) {
            System.out.println("Error: choose one of the options\n");
            System.out.println("tail [-c num|-n num] [-o ofile] file0 file1 file2 …\n");
            return;
        }

        if (c == null && n > 0) {
            Tail.flag = "lines";
            Tail.number = n;
        }

        if (c > 0 && n == null) {
            Tail.flag = "chars";
            Tail.number = c;
        }

        else System.out.println("Error: illegal option\n");

        Tail.cutter(reader, writer);
    }
}
