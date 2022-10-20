package markup;


import java.util.List;
import java.util.Map;

public class Paragraph extends AbstractElement implements BlockElement {
    public Paragraph(List<SpanElement> elements) {
        super(Map.of(
                MarkupType.MARKDOWN, new MarkupTags("", ""),
                MarkupType.HTML, new MarkupTags("", ""),
                MarkupType.TEX, new MarkupTags("", "")
        ), elements);
    }
}
