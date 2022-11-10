package markup;

import java.util.List;
import java.util.Map;

public class Underline extends AbstractElement implements SpanElement {
    private static final Map<MarkupType, MarkupTags> markupTags = Map.of(
            MarkupType.MARKDOWN, new MarkupTags("++", "++"),
            MarkupType.HTML, new MarkupTags("<u>", "</u>")
    );

    public Underline(List<SpanElement> elements) {
        super(elements);
    }

    public Underline(SpanElement elements) {
        super(elements);
    }

    public Underline(String text) {
        super(text);
    }

    @Override
    protected MarkupTags getTags(MarkupType markupType) {
        return markupTags.get(markupType);
    }
}
