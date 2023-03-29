import java.io.*;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

public class WordStatWordsShingles extends WordStat {
    private final Map<String, Integer> occurrences = new TreeMap<>();

    private void addNewOccurrences(String token) {
        occurrences.compute(token.toLowerCase(), (k, v) -> v == null ? 1 : v + 1);
    }

    @Override
    protected void tokenConsume(String token) {
        if (token.length() < 3) {
            addNewOccurrences(token);
            return;
        }

        for (int i = 0; i < token.length() - 2; i++) {
            addNewOccurrences(token.substring(i, i + 3));
        }
    }

    @Override
    protected void performWrite(BufferedWriter writer) throws IOException {
        for (Entry<String, Integer> occurrence : occurrences.entrySet()) {
            writer.write(occurrence.getKey() + ' ' + occurrence.getValue());
            writer.newLine();
        }
    }

    public static void main(String[] args) {
        WordStatWordsShingles wordStat = new WordStatWordsShingles();
        wordStat.perform(args);
    }
}

