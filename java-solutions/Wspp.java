import java.io.*;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Wspp extends WordStat {
    private Map<String, Occurances> occurances = new LinkedHashMap<>();
    private int tokensCount = 0;

    private void addNewOccurances(String token) {
        tokensCount++;
        token = token.toLowerCase();
        Occurances oldValue = occurances.get(token);
        if (oldValue == null) {
            oldValue = occurances.put(token, new Occurances(tokensCount));
        } else {
            oldValue.addIndex(tokensCount);
        }
    }

    @Override
    protected void tokenConsum(String token) {
        addNewOccurances(token);
    }

    @Override
    protected void performWrite(BufferedWriter writer) throws IOException {
        for (Entry<String, Occurances> occurency : occurances.entrySet()) {
            writer.write(occurency.getValue().toString(occurency.getKey()));
            writer.newLine();
        }
    }

    public static void main(String[] args) {
        WordStat wordStat = new Wspp();
        wordStat.perform(args);
    }
}
