import scanner.Scanner;
import wordstat.WordStat;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;

public class WsppCountPosition extends WordStat {
    private final Map<String, OccurrencesPosition> occurrences = new LinkedHashMap<>();

    private int horizontalPos = 1;
    private int verticalPos = 0;

    private static boolean isCharWordPart(char ch) {
        return ch == '\''
                || Character.isLetter(ch)
                || Character.getType(ch) == Character.DASH_PUNCTUATION;
    }

    @Override
    protected void tokenConsume(String token) {
        token = token.toLowerCase();
        OccurrencesPosition oldValue = occurrences.get(token);
        if (oldValue == null) {
            occurrences.put(token, new OccurrencesPosition(verticalPos, horizontalPos));
        } else {
            oldValue.addPos(verticalPos, horizontalPos);
        }
    }

    private List<String> tokenize(String line) {
        List<String> tokens = new ArrayList<>();
        int index = 0;

        while (index < line.length()) {
            int tokenStart = index;

            while (index < line.length() && isCharWordPart(line.charAt(index))) {
                index++;
            }

            if (tokenStart != index) {
                String sub = line.substring(tokenStart, index);
                tokens.add(sub);
            }
            index++;
        }

        return tokens;
    }

    @Override
    public void read(String inputFileName)
            throws IOException {
        try (Scanner in = new Scanner(inputFileName)) {
            while (in.hasNextLine()) {
                String line = in.nextLine();
                verticalPos++;

                for (String token : tokenize(line)) {
                    tokenConsume(token);
                    horizontalPos++;
                }
                horizontalPos = 1;
            }
        }
    }

    @Override
    protected void performWrite(BufferedWriter writer) throws IOException {
        List<Entry<String, OccurrencesPosition>> positions = new ArrayList<>(occurrences.entrySet());

        Collections.sort(positions, Comparator.comparingInt(a -> a.getValue().getCount()));

        for (Entry<String, OccurrencesPosition> occurrence : positions) {
            writer.write(occurrence.getValue().toString(occurrence.getKey()));
            writer.newLine();
        }
    }

    public static void main(String[] args) {
        WordStat wordStat = new WsppCountPosition();
        wordStat.perform(args);
    }
}
