package wordStat;

public class Occurrence {
    public int count;
    public final String token;

    public Occurrence(String token) {
        count = 1;
        this.token = token.toLowerCase();
    }

    public boolean tokenEquals(String token) {
        return this.token.equals(token.toLowerCase());
    }
}