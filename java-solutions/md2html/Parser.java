package md2html;

import markup.MarkupType;

public interface Parser {
    void parse(String[] data);
    void toMarkdown(StringBuilder sb);
    void toHtml(StringBuilder sb);
    void toTex(StringBuilder sb);
    void toBBCode(StringBuilder sb);
}
