package markup.parser;

import markup.*;

import java.util.*;

public class MarkdownDocumentParser implements Parser {
    private final List<BlockElement> blocks = new ArrayList<>();
    private final boolean useHtmlEscaping;

    public MarkdownDocumentParser(boolean useHtmlEscaping) {
        this.useHtmlEscaping = useHtmlEscaping;
    }

    @Override
    public Document parse(String text) {
        for (String blockText : splitToBlocksText(text)) {
            BlockElement block = (new MarkdownBlockParser(useHtmlEscaping)).parse(blockText);
            blocks.add(block);
        }

        return new Document(blocks);
    }

    private static List<String> splitToBlocksText(String text) {
        List<String> blocksText = new ArrayList<>();
        int blockStart = -1;
        boolean blockEmpty = true;

        text = text + "\n\n"; // :NOTE: много памяти

        for (int i = 0; i < text.length() - 1; i++) {
            if (text.charAt(i) != '\n') {
                if (blockStart != -1) {
                    blockEmpty = false;
                } else {
                    blockStart = i;
                }
            } else if (text.charAt(i + 1) == '\n' && !blockEmpty) {
                blocksText.add(text.substring(blockStart, i));
                blockStart = -1;
                blockEmpty = true;
            }
        }

        return blocksText;
    }
}
