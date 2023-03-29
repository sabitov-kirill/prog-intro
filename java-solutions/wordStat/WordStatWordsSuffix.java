package wordStat;

import java.io.*;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

public class WordStatWordsSuffix extends WordStat {
    private final Map<String, Integer> occurrences = new TreeMap<>();

    private void addNewOccurrences(String token) {
        occurrences.compute(token.toLowerCase(), (k, v) -> v == null ? 1 : v + 1);
    }

    @Override
    protected void tokenConsume(String token) {
        if (token.length() > 3) {
            token = token.substring(token.length() - 3);
        }
        addNewOccurrences(token);
    }

    @Override
    protected void performWrite(BufferedWriter writer) throws IOException {
        for (Entry<String, Integer> occurrence : occurrences.entrySet()) {
            writer.write(occurrence.getKey() + ' ' + occurrence.getValue());
            writer.newLine();
        }
    }

    public static void main(String[] args) {
        WordStatWordsSuffix wordStat = new WordStatWordsSuffix();
        wordStat.perform(args);
    }
}