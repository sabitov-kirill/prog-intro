package markup;

/**
 * General page markup element
 */
public interface Element {
    /**
     * Convert element to Markdown markup representation function.
     *
     * @param sb - string builder to apply element markup representation in.
     */
    void toMarkdown(StringBuilder sb);

    /**
     * Convert element to HTML markup representation function.
     *
     * @param sb - string builder to apply element markup representation in.
     */
    void toHtml(StringBuilder sb);

    /**
     * Convert element to LaTex markup representation function.
     *
     * @param sb - string builder to apply element markup representation in.
     */
    void toTex(StringBuilder sb);
}
