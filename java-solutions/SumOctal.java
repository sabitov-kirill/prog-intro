import java.util.ArrayList;
import java.util.List;

public class SumOctal {
    public static void main(String[] args) {
        String text = String.join(" ", args);
        List<String> tokens = tokenize(text);

        int sum = tokens.stream()
                .mapToInt(SumOctal::parseNumber)
                .sum();

        System.out.println(sum);
    }

    private static List<String> tokenize(String input) {
        List<String> tokens = new ArrayList<>();
        String numberString = new String();
        Boolean isPrevCharWhitespace = true;

        for (char ch : (input + " ").toCharArray()) {
            if (!Character.isWhitespace(ch)) {
                numberString += ch;
                isPrevCharWhitespace = false;
            } else if (!isPrevCharWhitespace) {
                tokens.add(numberString);
                numberString = "";
                isPrevCharWhitespace = true;
            }
        }

        return tokens;
    }

    private static int parseNumber(String stringRepresentation) {
        if (stringRepresentation.endsWith("o") || stringRepresentation.endsWith("O")) {
            String withoutSuffix = stringRepresentation.substring(0, stringRepresentation.length() - 1);
            return Integer.parseInt(withoutSuffix, 8);
        }
        return Integer.parseInt(stringRepresentation);
    }
}
