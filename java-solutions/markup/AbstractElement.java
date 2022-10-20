package markup;

import java.util.List;
import java.util.Map;

public abstract class AbstractElement implements Element {
    protected final List<? extends Element> elements;
    protected final Map<MarkupType, MarkupTags> markupTags;

    protected AbstractElement(Map<MarkupType, MarkupTags> markupTags, List<? extends Element> elements) {
        this.elements = List.copyOf(elements);
        this.markupTags = markupTags;
    }

    protected void toMarkup(StringBuilder sb, MarkupType markupType) {
        MarkupTags tags = markupTags.get(markupType);
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
