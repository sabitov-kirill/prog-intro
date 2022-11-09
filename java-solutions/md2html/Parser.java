package md2html;

import markup.Element;

public interface Parser {
    Element parse(String text);
}
