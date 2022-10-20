package markup;

import java.util.List;
import java.util.Map;

public class OrderedList extends AbstractElement implements BlockElement {
    public OrderedList(List<ListItem> elements) {
        super(Map.of(
                MarkupType.HTML, new MarkupTags("<ol>", "</ol>"),
                MarkupType.TEX, new MarkupTags("\\begin{enumerate}", "\\end{enumerate}")
        ), elements);
    }
}
