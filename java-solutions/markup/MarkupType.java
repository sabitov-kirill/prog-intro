package markup;

import java.util.function.BiConsumer;

public enum MarkupType {
    MARKDOWN(Element::toMarkdown),
    HTML(Element::toHtml),
    TEX(Element::toTex),
    BBCODE(Element::toBBCode);

    public final BiConsumer<Element, StringBuilder> elementToMarkup;

    MarkupType(BiConsumer<Element, StringBuilder> elementToMarkup) {
        this.elementToMarkup = elementToMarkup;
    }
}
