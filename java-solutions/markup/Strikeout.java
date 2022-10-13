package markup;

import java.util.List;

public class Strikeout extends Paragraph {
    public Strikeout(List<Element> elements) {
        super(elements);
    }

    @Override
    protected void beginMarkup(StringBuilder sb) {
        sb.append("~");
    }

    @Override
    protected void endMarkup(StringBuilder sb) {
        sb.append("~");
    }
}
