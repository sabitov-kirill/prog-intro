package markup.parser;

import markup.Element;

public interface Parser {
    Element parse(String text);
}
