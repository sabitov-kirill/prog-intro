package markup;

import java.util.List;

public class Document extends AbstractElement {
    public Document(List<BlockElement> elements) {
        super(elements);
    }

    public Document(BlockElement... elements) {
        super(elements);
    }

    @Override
    protected MarkupTags getTags(MarkupType markupType) {
        return null;
    }

    protected void toMarkup(StringBuilder sb, MarkupType markupType) {
        boolean separateBlocksWithEOL = switch (markupType) {
            case HTML -> false;
            case MARKDOWN, TEX, BBCODE -> true;
        };

        for (int i = 0; i < elements.size(); i++) {
            markupType.elementToMarkup.accept(elements.get(i), sb);
            if (separateBlocksWithEOL && i != elements.size() - 1) {
                sb.append('\n');
            }
        }
    }
}
