package markup;


import java.util.List;
import java.util.Map;

public class Paragraph extends AbstractElement implements BlockElement {
    private static final Map<MarkupType, MarkupTags> markupTags = Map.of(
            MarkupType.MARKDOWN, new MarkupTags("", ""),
            MarkupType.HTML, new MarkupTags("", ""),
            MarkupType.TEX, new MarkupTags("", ""),
            MarkupType.BBCODE, new MarkupTags("", "")
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
