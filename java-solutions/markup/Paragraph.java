package markup;


import java.util.List;
import java.util.Map;

public class Paragraph extends AbstractElement implements BlockElement {
    private static final Map<MarkupType, MarkupTags> markupTags = Map.of(
            MarkupType.MARKDOWN, new MarkupTags("", "\n"),
            MarkupType.HTML, new MarkupTags("<p>", "</p>\n"),
            MarkupType.TEX, new MarkupTags("", "\n"),
            MarkupType.BBCODE, new MarkupTags("", "\n")
    );

    public Paragraph(List<SpanElement> elements) {
        super(elements);
    }

    public Paragraph(SpanElement... elements) {
        super(elements);
    }

    public Paragraph(String text) {
        super(text);
    }

    @Override
    protected MarkupTags getTags(MarkupType markupType) {
        return markupTags.get(markupType);
    }
}
