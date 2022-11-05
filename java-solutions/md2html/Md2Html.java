package md2html;

import scanner.Scanner;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class Md2Html {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.err.println("Input/Output file names not passed.");
            return;
        }

        MarkdownParser parser = new MarkdownParser(true);
        StringBuilder sb = new StringBuilder();

        try (Scanner in = new Scanner(args[0], Scanner.DEFAULT_CHARSET)) {
            while (in.hasNextLine()) {
                String line = in.nextLine();

                if (!line.isBlank()) {
                    sb.append(line).append(System.lineSeparator());
                } else if (!sb.isEmpty()) {
                    parser.parseBlock(sb.deleteCharAt(sb.length() - 1).toString());
                    sb.setLength(0);
                }
            }

            if (!sb.isEmpty()) {
                parser.parseBlock(sb.deleteCharAt(sb.length() - 1).toString());
            }
        } catch (FileNotFoundException e) {
            System.err.println("Input file not found.");
            return;
        } catch (IOException e) {
            System.err.println("Error during reading input file \"" + args[0] + "\": " + e.getMessage());
            return;
        }

        sb.setLength(0);
        try (FileWriter out = new FileWriter(args[1], StandardCharsets.UTF_8)) {
            parser.toHtml(sb);
            out.write(sb.toString());
        } catch (IOException e) {
            System.err.println("Error during writing to output file \"" + args[1] + "\": " + e.getMessage());
        }
    }
}
