public class Occurance {
    public int count;
    public String token;

    public Occurance(String token) {
        count = 1;
        this.token = token.toLowerCase();
    }

    public boolean tokenEquals(String token) {
        return this.token.equals(token.toLowerCase());
    }
}