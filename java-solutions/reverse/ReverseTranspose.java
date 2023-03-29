import scanner.Scanner;

import java.io.IOException;

public class ReverseTranspose extends Reverse {
    @Override
    public void print() {
        int maxLen = 0;
        for (int line = 0; line < linesCurrentCount; line++) {
            maxLen = Math.max(maxLen, linesNumbers[line].length);
        }

        for (int i = 0; i < maxLen; i++) {
            for (int j = 0; j < linesCurrentCount; j++) {
                if (i >= linesNumbers[j].length) {
                    continue;
                }

                System.out.print(linesNumbers[j][i]);
                System.out.print(' ');
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        Scanner inputScanner = new Scanner(System.in);
        ReverseTranspose reverse = new ReverseTranspose();

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
