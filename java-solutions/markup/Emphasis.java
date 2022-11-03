package markup;

import java.util.List;
import java.util.Map;

public class Emphasis extends AbstractElement implements SpanElement {
    private static final Map<MarkupType, MarkupTags> markupTags = Map.of(
            MarkupType.MARKDOWN, new MarkupTags("*", "*"),
            MarkupType.HTML, new MarkupTags("<em>", "</em>"),
            MarkupType.TEX, new MarkupTags("\\emph{", "}"),
            MarkupType.BBCODE, new MarkupTags("[i]", "[/i]")
    );

    public Emphasis(List<SpanElement> elements) {
        super(elements);
    }

    public Emphasis(SpanElement... elements) {
        super(elements);
    }

    public Emphasis(String text) {
        super(text);
    }

    @Override
    protected MarkupTags getTags(MarkupType markupType) {
        return markupTags.get(markupType);
    }
}
