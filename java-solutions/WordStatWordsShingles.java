import java.io.*;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

public class WordStatWordsShingles extends WordStat {
    private Map<String, Integer> occurances = new TreeMap<>();

    private void addNewOccurances(String token) {
        occurances.compute(token.toLowerCase(), (k, v) -> v == null ? 1 : v + 1);
    }

    @Override
    protected void tokenConsum(String token) {
        if (token.length() < 3) {
            addNewOccurances(token);
            return;
        }

        for (int i = 0; i < token.length() - 2; i++) {
            addNewOccurances(token.substring(i, i + 3));
        }
    }

    @Override
    protected void performWrite(BufferedWriter writer) throws IOException {
        for (Entry<String, Integer> occurency : occurances.entrySet()) {
            writer.write(occurency.getKey() + ' ' + occurency.getValue());
            writer.newLine();
        }
    }

    public static void main(String[] args) {
        WordStatWordsShingles wordStat = new WordStatWordsShingles();
        wordStat.perform(args);
    }
}

