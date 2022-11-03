package markup;

import java.util.List;
import java.util.Map;

public class OrderedList extends AbstractElement implements BlockElement {
    private static final Map<MarkupType, MarkupTags> markupTags = Map.of(
            MarkupType.HTML, new MarkupTags("<ol>", "</ol>"),
            MarkupType.TEX, new MarkupTags("\\begin{enumerate}", "\\end{enumerate}")
    );

    public OrderedList(List<ListItem> elements) {
        super(elements);
    }
    public OrderedList(ListItem... elements) {
        super(elements);
    }

    public OrderedList(String text) {
        super(text);
    }

    @Override
    protected MarkupTags getTags(MarkupType markupType) {
        return markupTags.get(markupType);
    }
}
