package markup;

import java.util.List;
import java.util.Map;
import java.util.function.IntFunction;

public class Header extends AbstractElement implements BlockElement {
    private static final Map<MarkupType, IntFunction<MarkupTags>> markupTags = Map.of(
            MarkupType.MARKDOWN, (level) -> new MarkupTags("#".repeat(level) + " ", ""),
            MarkupType.HTML, (level) -> new MarkupTags("<h" + level +">", "</h" + level + ">")
    );
    private final int level;

    public Header(int level, List<SpanElement> elements) {
        super(elements);
        this.level = level;
    }

    public Header(int level, SpanElement... elements) {
        super(elements);
        this.level = level;
    }

    public Header(int level, String text) {
        super(text);
        this.level = level;
    }

    @Override
    protected MarkupTags getTags(MarkupType markupType) {
        return markupTags.get(markupType).apply(level);
    }
}
