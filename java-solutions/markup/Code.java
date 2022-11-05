package markup;

import java.util.List;
import java.util.Map;

public class Code extends AbstractElement implements SpanElement {
    private static final Map<MarkupType, MarkupTags> markupTags = Map.of(
            MarkupType.MARKDOWN, new MarkupTags("`", "`"),
            MarkupType.HTML, new MarkupTags("<code>", "</code>"),
            MarkupType.TEX, new MarkupTags("\\begin{lstlisting}", "\\end{lstlisting}")
    );

    public Code(List<SpanElement> elements) {
        super(elements);
    }

    public Code(SpanElement... elements) {
        super(elements);
    }

    public Code(String text) {
        super(text);
    }

    @Override
    protected MarkupTags getTags(MarkupType markupType) {
        return markupTags.get(markupType);
    }
}
