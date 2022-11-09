package markup;

import java.util.List;

public class Header extends AbstractElement implements BlockElement {
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
        return switch (markupType) {
            case MARKDOWN -> new MarkupTags("#".repeat(level) + " ", "\n");
            case HTML -> new MarkupTags("<h" + level +">", "</h" + level + ">\n");
            default -> null;
        };
    }
}
