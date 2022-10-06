import java.io.*;
import java.util.Arrays;

public class WordStatInput extends WordStat {
    protected int occurancesCount = 0;
    protected Occurance[] occurances = new Occurance[10];

    protected void addNewOccurance(String token) {
        if (occurancesCount >= occurances.length) {
            occurances = Arrays.copyOf(occurances, occurances.length * 2);
        }

        occurances[occurancesCount++] = new Occurance(token);
    }

    @Override
    protected void tokenConsum(String token) {
        for (int i = 0; i < occurancesCount; i++) {
            if (occurances[i].tokenEquals(token)) {
                occurances[i].count += 1;
                return;
            }
        }

        addNewOccurance(token);
    }

    @Override
    protected void performWrite(BufferedWriter writer) throws IOException {
        for (int i = 0; i < occurancesCount; i++) {
            writer.write(occurances[i].token + ' ' + occurances[i].count);
            writer.newLine();
        }
    }

    public static void main(String[] args) {
        WordStatInput wordStat = new WordStatInput();
        wordStat.perform(args);
    }
}
