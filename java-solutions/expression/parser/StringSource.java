package expression.parser;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class StringSource implements CharSource {
    private final String data;
    private int pos;
    private int anchorPos;

    public StringSource(final String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }

    @Override
    public boolean hasNext() {
        return pos < data.length();
    }

    @Override
    public char next() {
        return data.charAt(pos++);
    }

    @Override
    public int getPosition() {
        return pos;
    }

    @Override
    public void setAnchor() {
        anchorPos = pos;
    }

    @Override
    public void returnToAnchor() {
        pos = anchorPos;
    }
}
