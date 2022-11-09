package markup;

import java.util.List;
import java.util.Map;

public class UnorderedList extends AbstractElement implements BlockElement {
    public static final Map<MarkupType, MarkupTags> markupTags = Map.of(
            MarkupType.HTML, new MarkupTags("<ul>", "</ul>"),
            MarkupType.TEX, new MarkupTags("\\begin{itemize}", "\\end{itemize}")
    );

    public UnorderedList(List<ListItem> elements) {
        super(elements);
    }

    public UnorderedList(ListItem... elements) {
        super(elements);
    }

    @Override
    protected MarkupTags getTags(MarkupType markupType) {
        return markupTags.get(markupType);
    }
}
