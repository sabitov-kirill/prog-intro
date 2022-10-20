package markup;

import java.util.List;
import java.util.Map;

public class Strong extends AbstractElement implements SpanElement {
    public Strong(List<SpanElement> elements) {
        super(Map.of(
                MarkupType.MARKDOWN, new MarkupTags("__", "__"),
                MarkupType.HTML, new MarkupTags("<strong>", "</strong>"),
                MarkupType.TEX, new MarkupTags("\\textbf{", "}")
        ), elements);
    }
}
