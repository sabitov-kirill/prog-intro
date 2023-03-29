package wordStat;

import java.io.*;
import java.util.Arrays;

public class WordStatInput extends WordStat {
    protected int occurrencesCount = 0;
    protected Occurrence[] occurrences = new Occurrence[10];

    protected void addNewOccurrence(String token) {
        if (occurrencesCount >= occurrences.length) {
            occurrences = Arrays.copyOf(occurrences, occurrences.length * 2);
        }

        occurrences[occurrencesCount++] = new Occurrence(token);
    }

    @Override
    protected void tokenConsume(String token) {
        for (int i = 0; i < occurrencesCount; i++) {
            if (occurrences[i].tokenEquals(token)) {
                occurrences[i].count += 1;
                return;
            }
        }

        addNewOccurrence(token);
    }

    @Override
    protected void performWrite(BufferedWriter writer) throws IOException {
        for (int i = 0; i < occurrencesCount; i++) {
            writer.write(occurrences[i].token + ' ' + occurrences[i].count);
            writer.newLine();
        }
    }

    public static void main(String[] args) {
        WordStatInput wordStat = new WordStatInput();
        wordStat.perform(args);
    }
}
