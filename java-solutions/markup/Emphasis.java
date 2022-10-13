package markup;

import java.util.List;

public class Emphasis extends Paragraph {
    public Emphasis(List<Element> elements) {
        super(elements);
    }

    @Override
    protected void beginMarkup(StringBuilder sb) {
        sb.append("*");
    }

    @Override
    protected void endMarkup(StringBuilder sb) {
        sb.append("*");
    }
}
