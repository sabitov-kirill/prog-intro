import scanner.Scanner;

import java.io.IOException;
import java.util.stream.*;

public class ReverseSum extends Reverse {
    protected int[] linesSum;
    protected int[] columnsSum;

    public void calculateMatrixData() {
        linesSum = Stream.of(linesNumbers)
                .limit(linesCurrentCount)
                .mapToInt(lineNumbers -> IntStream.of(lineNumbers).sum())
                .toArray();

        int maxLen = 0;
        for (int line = 0; line < linesCurrentCount; line++) {
            maxLen = Math.max(maxLen, linesNumbers[line].length);
        }

        columnsSum = new int[maxLen];
        for (int col = 0; col < maxLen; col++) {
            int columnSum = 0;
            for (int line = 0; line < linesCurrentCount; line++) {
                if (col < linesNumbers[line].length) {
                    columnSum += linesNumbers[line][col];
                }
            }

            columnsSum[col] = columnSum;
        }
    }

    @Override
    public void print() {
        if (linesSum == null || columnsSum == null) {
            return;
        }

        for (int line = 0; line < linesCurrentCount; line++) {
            for (int col = 0; col < linesNumbers[line].length; col++) {
                int matrixValue = linesSum[line] + columnsSum[col] - linesNumbers[line][col];
                System.out.print(matrixValue);
                System.out.print(' ');
            }
            System.out.print('\n');
        }
    }

    public static void main(String[] args) {
        Scanner inputScanner = new Scanner(System.in);
        ReverseSum reverse = new ReverseSum();

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

        reverse.calculateMatrixData();
        reverse.print();
    }
}
