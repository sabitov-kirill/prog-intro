package markup;

import java.util.List;
import java.util.Map;

public class Strong extends AbstractElement implements SpanElement {
    private static final Map<MarkupType, MarkupTags> markupTags = Map.of(
            MarkupType.MARKDOWN, new MarkupTags("__", "__"),
            MarkupType.HTML, new MarkupTags("<strong>", "</strong>"),
            MarkupType.TEX, new MarkupTags("\\textbf{", "}"),
            MarkupType.BBCODE, new MarkupTags("[b]", "[/b]")
    );

    public Strong(List<SpanElement> elements) {
        super(elements);
    }

    public Strong(SpanElement... elements) {
        super(elements);
    }

    public Strong(String text) {
        super(text);
    }

    @Override
    protected MarkupTags getTags(MarkupType markupType) {
        return markupTags.get(markupType);
    }
}
