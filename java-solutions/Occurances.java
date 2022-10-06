import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Occurances {
    private List<Integer> indices;

    public Occurances(int firstIndex) {
        indices = new ArrayList<>(Arrays.asList(firstIndex));
    }

    public void addIndex(int index) {
        indices.add(index);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(indices.size());
        sb.append(' ');
        for (int ind : indices) {
            sb.append(ind);
           sb.append(' ');
        }

        return sb.toString();
    }

    public String toString(String token) {
        StringBuilder sb = new StringBuilder();
        sb.append(token);
        sb.append(' ');
        sb.append(indices.size());
        sb.append(' ');
        for (int ind : indices) {
            sb.append(ind);
            sb.append(' ');
        }

        return sb.substring(0, sb.length() - 1).toString();
    }
}
