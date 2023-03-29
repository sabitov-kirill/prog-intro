import scanner.Scanner;

import java.io.IOException;

public class ReverseAbc {
    protected int linesMaxCount = 4;
    protected int linesCurrentCount = 0;
    protected String[][] linesNumbers = new String[linesMaxCount][];

    public void startNewLine() {
        if (linesCurrentCount >= linesMaxCount) {
            linesMaxCount *= 2;

            String[][] resizedLinesNumbers = new String[linesMaxCount][];
            System.arraycopy(linesNumbers, 0, resizedLinesNumbers, 0, linesCurrentCount);
            linesNumbers = resizedLinesNumbers;
        }
        linesCurrentCount++;
    }

    public void addTokenizedLine(String str) {
        startNewLine();

        str = str.trim();
        if (!str.isEmpty()) {
            linesNumbers[linesCurrentCount - 1] = str.split("\s+");
        } else {
            linesNumbers[linesCurrentCount - 1] = new String[0];
        }
    }

    public void print() {
        for (int lineIndex = linesCurrentCount - 1; lineIndex >= 0; lineIndex--) {
            for (int numberIndex = linesNumbers[lineIndex].length - 1; numberIndex >= 0; numberIndex--) {
                System.out.print(linesNumbers[lineIndex][numberIndex]);
                System.out.print(' ');
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        Scanner inputScanner = new Scanner(System.in);
        ReverseAbc reverse = new ReverseAbc();

        try {
            try {
                while (inputScanner.hasNextLine()) {
                    String inputString = inputScanner.nextLine();
                    reverse.addTokenizedLine(inputString);
                }
            } finally {
                inputScanner.close();
            }
        } catch (IOException e) {
            System.err.println("I/O error occurred while reading input stream.");
        }
        reverse.print();
    }
}
