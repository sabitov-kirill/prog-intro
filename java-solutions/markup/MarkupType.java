package markup;

import java.util.function.BiConsumer;

public enum MarkupType {
    MARKDOWN("md", Element::toMarkdown),
    HTML("html", Element::toHtml),
    TEX("tex", Element::toTex);

    public final String name;
    public final BiConsumer<Element, StringBuilder> elementToMarkup;

    MarkupType(String name, BiConsumer<Element, StringBuilder> elementToMarkup) {
        this.name = name;
        this.elementToMarkup = elementToMarkup;
    }
}
