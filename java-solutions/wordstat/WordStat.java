package wordstat;

import scanner.Scanner;

import java.io.*;
import java.nio.charset.StandardCharsets;

public abstract class WordStat {
    protected abstract void performWrite(BufferedWriter writer) throws IOException;
    protected abstract void tokenConsume(String token);


    public void read(String inputFileName)
            throws IOException {
        try (Scanner in = new Scanner(inputFileName)) {
            while (in.hasNextWord()) {
                String word = in.nextWord();
                tokenConsume(word);
            }
        }
    }

    public void write(String outputFileName)
            throws IOException {

        try (BufferedWriter out = new BufferedWriter(
                new OutputStreamWriter(
                        new FileOutputStream(outputFileName),
                        StandardCharsets.UTF_8
                )
        )) {
            performWrite(out);
        }
    }
    
    public void perform(String[] args) {
        if (args.length < 2) {
            System.err.println("Input and/or output files isn't provided.");
            return;
        }
        String inFileName = args[0];
        String outFileName = args[1];

        try {
            read(inFileName);
            write(outFileName);
        } catch (FileNotFoundException e) {
            System.err.println("Specified input file not found: " + e.getMessage());
        } catch (UnsupportedEncodingException e) {
            System.err.println("Utf8 encoding isn't supported on your platform: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error occurred while reading/writing files: " + e.getMessage());
        }
    }
}
