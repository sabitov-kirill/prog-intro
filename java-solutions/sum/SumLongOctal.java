package sum;

import java.util.ArrayList;
import java.util.List;

public class SumLongOctal {
    public static void main(String[] args) {
        String text = String.join(" ", args);
        List<String> tokens = tokenize(text);

        long sum = tokens.stream()
                .mapToLong(SumLongOctal::parseNumber)
                .sum();

        System.out.println(sum);
    }

    private static List<String> tokenize(String input) {
        List<String> tokens = new ArrayList<>();
        StringBuilder numberString = new StringBuilder();
        boolean isPrevCharWhitespace = true;

        for (char ch : (input + " ").toCharArray()) {
            if (!Character.isWhitespace(ch)) {
                numberString.append(ch);
                isPrevCharWhitespace = false;
            } else if (!isPrevCharWhitespace) {
                tokens.add(numberString.toString());
                numberString = new StringBuilder();
                isPrevCharWhitespace = true;
            }
        }

        return tokens;
    }

    private static long parseNumber(String stringRepresentation) {
        if (stringRepresentation.endsWith("o") || stringRepresentation.endsWith("O")) {
            String withoutSuffix = stringRepresentation.substring(0, stringRepresentation.length() - 1);
            return Long.parseLong(withoutSuffix, 8);
        }
        return Long.parseLong(stringRepresentation);
    }
}
