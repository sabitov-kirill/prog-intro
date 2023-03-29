package wspp;

import wordStat.WordStat;

import java.io.*;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Wspp extends WordStat {
    private final Map<String, Occurrences> occurrences = new LinkedHashMap<>();
    private int tokensCount = 0;

    private void addNewOccurrences(String token) {
        tokensCount++;
        token = token.toLowerCase();
        Occurrences oldValue = occurrences.get(token);
        if (oldValue == null) {
            occurrences.put(token, new Occurrences(tokensCount));
        } else {
            oldValue.addIndex(tokensCount);
        }
    }

    @Override
    protected void tokenConsume(String token) {
        addNewOccurrences(token);
    }

    @Override
    protected void performWrite(BufferedWriter writer) throws IOException {
        for (Entry<String, Occurrences> occurrence : occurrences.entrySet()) {
            writer.write(occurrence.getValue().toString(occurrence.getKey()));
            writer.newLine();
        }
    }

    public static void main(String[] args) {
        WordStat wordStat = new Wspp();
        wordStat.perform(args);
    }
}
