import java.io.*;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

public class WordStatWordsSuffix extends WordStat {
    private Map<String, Integer> occurances = new TreeMap<>();

    private void addNewOccurances(String token) {
        occurances.compute(token.toLowerCase(), (k, v) -> v == null ? 1 : v + 1);
    }

    @Override
    protected void tokenConsum(String token) {
        if (token.length() > 3) {
            token = token.substring(token.length() - 3, token.length());
        }
        addNewOccurances(token);
    }

    @Override
    protected void performWrite(BufferedWriter writer) throws IOException {
        for (Entry<String, Integer> occurency : occurances.entrySet()) {
            writer.write(occurency.getKey() + ' ' + occurency.getValue());
            writer.newLine();
        }
    }

    public static void main(String[] args) {
        WordStatWordsSuffix wordStat = new WordStatWordsSuffix();
        wordStat.perform(args);
    }
}