package markup;

import java.util.List;

public abstract class AbstractElement implements Element {
    protected final List<? extends Element> elements;

    protected AbstractElement(List<? extends Element> elements) {
        this.elements = List.copyOf(elements);
    }

    protected AbstractElement(Element... elements) {
        this.elements = List.of(elements);
    }

    protected AbstractElement(String text) {
        this.elements = List.of(new Text(text));
    }

    protected abstract MarkupTags getTags(MarkupType markupType);

    protected void toMarkup(StringBuilder sb, MarkupType markupType) {
        MarkupTags tags = getTags(markupType);
        if (tags == null) {
            return;
        }

        sb.append(tags.begin());
        elements.forEach(element -> markupType.elementToMarkup.accept(element, sb));
        sb.append(tags.end());
    }

    @Override
    public void toMarkdown(StringBuilder sb) {
        toMarkup(sb, MarkupType.MARKDOWN);
    }

    @Override
    public void toHtml(StringBuilder sb) {
        toMarkup(sb, MarkupType.HTML);
    }

    @Override
    public void toTex(StringBuilder sb) {
        toMarkup(sb, MarkupType.TEX);
    }

    @Override
    public void toBBCode(StringBuilder sb) {
        toMarkup(sb, MarkupType.BBCODE);
    }
}
