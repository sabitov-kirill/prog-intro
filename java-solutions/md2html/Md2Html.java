package md2html;

import markup.Element;

import java.io.*;
import java.nio.file.*;
import java.nio.charset.StandardCharsets;

public class Md2Html {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.err.println("Input/Output file names not passed.");
            return;
        }

        StringBuilder output = new StringBuilder();

        try {
            String text = Files.readString(Paths.get(args[0]), StandardCharsets.UTF_8);
            MarkdownDocumentParser parser = new MarkdownDocumentParser(true);
            Element el = parser.parse(text);
            el.toHtml(output);
        } catch (IOException e) {
            System.err.println("Error during reading input file \"" + args[0] + "\": " + e.getMessage());
            return;
        }

        try (FileWriter out = new FileWriter(args[1], StandardCharsets.UTF_8)) {
            out.write(output.toString());
        } catch (IOException e) {
            System.err.println("Error during writing to output file \"" + args[1] + "\": " + e.getMessage());
        }
    }
}
