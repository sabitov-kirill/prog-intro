package markup;

import java.util.List;
import java.util.Map;

public class UnorderedList extends AbstractElement implements BlockElement {
    public UnorderedList(List<ListItem> elements) {
        super(Map.of(
                MarkupType.HTML, new MarkupTags("<ul>", "</ul>"),
                MarkupType.TEX, new MarkupTags("\\begin{itemize}", "\\end{itemize}")
        ), elements);
    }
}
