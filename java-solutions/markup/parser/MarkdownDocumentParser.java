package markup.parser;

import markup.*;
import scanner.*;
import scanner.Scanner;

import java.io.IOException;
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
        Scanner scanner = new Scanner(text);
        StringBuilder sb = new StringBuilder();

        try {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();

                if (line.isBlank() && !sb.isEmpty()) {
                    sb.setLength(sb.length() - 1);
                    blocksText.add(sb.toString());
                    sb.setLength(0);
                } else if (!line.isBlank()) {
                    sb.append(line).append('\n');
                }
            }
        } catch (IOException e) {
            return blocksText;
        }

        if (!sb.isEmpty()) {
            sb.setLength(sb.length() - 1);
            blocksText.add(sb.toString());
        }

        return blocksText;
    }
}
