package markup;

import java.util.List;
import java.util.Map;

public class Emphasis extends AbstractElement implements SpanElement {
    public Emphasis(List<SpanElement> elements) {
        super(Map.of(
                MarkupType.MARKDOWN, new MarkupTags("*", "*"),
                MarkupType.HTML, new MarkupTags("<em>", "</em>"),
                MarkupType.TEX, new MarkupTags("\\emph{", "}")
        ), elements);
    }
}
