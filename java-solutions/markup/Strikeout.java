package markup;

import java.util.List;
import java.util.Map;

public class Strikeout extends AbstractElement implements SpanElement {
    private static final Map<MarkupType, MarkupTags> markupTags = Map.of(
            MarkupType.MARKDOWN, new MarkupTags("~", "~"),
            MarkupType.HTML, new MarkupTags("<s>", "</s>"),
            MarkupType.TEX, new MarkupTags("\\textst{", "}"),
            MarkupType.BBCODE, new MarkupTags("[s]", "[/s]")
    );

    public Strikeout(List<SpanElement> elements) {
        super(elements);
    }

    public Strikeout(SpanElement elements) {
        super(elements);
    }

    public Strikeout(String text) {
        super(text);
    }

    @Override
    protected MarkupTags getTags(MarkupType markupType) {
        return markupTags.get(markupType);
    }
}
