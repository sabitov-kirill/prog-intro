package markup;

import java.util.List;
import java.util.Map;

public class Strikeout extends AbstractElement implements SpanElement {
    public Strikeout(List<SpanElement> elements) {
        super(Map.of(
                MarkupType.MARKDOWN, new MarkupTags("~", "~"),
                MarkupType.HTML, new MarkupTags("<s>", "</s>"),
                MarkupType.TEX, new MarkupTags("\\textst{", "}")
        ), elements);
    }
}
