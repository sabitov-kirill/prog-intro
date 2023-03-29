package wspp;

import java.util.ArrayList;
import java.util.List;

public class Occurrences {
    private final List<Integer> indices;

    public Occurrences(int firstIndex) {
        indices = new ArrayList<>(List.of(firstIndex));
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

        return sb.substring(0, sb.length() - 1);
    }
}
