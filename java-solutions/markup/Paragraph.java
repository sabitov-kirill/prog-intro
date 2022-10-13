package markup;

import java.util.List;

public class Paragraph implements Element {
    protected final List<Element> elements;

    public Paragraph(List<Element> elements) {
        this.elements = List.copyOf(elements);
    }

    protected void beginMarkup(StringBuilder sb) {}

    protected void endMarkup(StringBuilder sb) {}

    @Override
    public void toMarkdown(StringBuilder sb) {
        beginMarkup(sb);
        for (Element element : elements) {
            element.toMarkdown(sb);
        }
        endMarkup(sb);
    }
}
