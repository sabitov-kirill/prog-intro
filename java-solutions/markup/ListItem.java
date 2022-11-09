package markup;

import java.util.List;
import java.util.Map;

public class ListItem extends AbstractElement implements ListItemElement {
    private static final Map<MarkupType, MarkupTags> markupTags = Map.of(
            MarkupType.HTML, new MarkupTags("<li>", "</li>"),
            MarkupType.TEX, new MarkupTags("\\item ", "")
    );

    public ListItem(List<BlockElement> elements) {
        super(elements);
    }

    public ListItem(BlockElement... elements) {
        super(elements);
    }

    @Override
    protected MarkupTags getTags(MarkupType markupType) {
        return markupTags.get(markupType);
    }
}
