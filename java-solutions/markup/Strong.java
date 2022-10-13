package markup;

import java.util.List;

public class Strong extends Paragraph {
    public Strong(List<Element> elements) {
        super(elements);
    }

    @Override
    protected void beginMarkup(StringBuilder sb) {
        sb.append("__");
    }

    @Override
    protected void endMarkup(StringBuilder sb) {
        sb.append("__");
    }
}
