import java.util.ArrayList;
import java.util.List;

public class OccurrencesPosition {
    private final List<String> positions;

    public OccurrencesPosition(int firstVerticalPos, int firstHorizontalPos) {
        positions = new ArrayList<>(List.of(String.valueOf(firstVerticalPos) + ":" + firstHorizontalPos));
    }

    public void addPos(int verticalPos, int horizontalPos) {
        positions.add(String.valueOf(verticalPos) + ":" + horizontalPos);
    }

    public int getCount() {
        return positions.size();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(positions.size());
        sb.append(' ');
        for (String pos : positions) {
            sb.append(pos);
           sb.append(' ');
        }

        return sb.toString();
    }

    public String toString(String token) {
        StringBuilder sb = new StringBuilder();
        sb.append(token);
        sb.append(' ');
        sb.append(positions.size());
        sb.append(' ');
        for (String pos : positions) {
            sb.append(pos);
            sb.append(' ');
        }

        return sb.substring(0, sb.length() - 1);
    }
}
