package markup;

import java.util.List;
import java.util.Map;

public class ListItem extends AbstractElement implements ListItemElement {
    public ListItem(List<BlockElement> elements) {
        super(Map.of(
                MarkupType.HTML, new MarkupTags("<li>", "</li>"),
                MarkupType.TEX, new MarkupTags("\\item ", "")
        ), elements);
    }
}
