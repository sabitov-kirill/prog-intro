package markup.parser;

import markup.*;

import java.util.*;

public class MarkdownBlockParser implements Parser {
    private static final Set<String> HTML_ESCAPING_CHARACTERS = Set.of("<", ">", "&");
    private static final Set<String> MD_TAGS = Set.of("_", "*", "`", "__", "**", "--", "++");
    
    private final boolean useHtmlEscaping;
    private final List<SpanElement> currentBlockChildren = new ArrayList<>();
    private final Stack<OpenedTag> currentBlockOpenedTags = new Stack<>();

    public MarkdownBlockParser(boolean useHtmlEscaping) {
        this.useHtmlEscaping = useHtmlEscaping;
    }

    @Override
    public BlockElement parse(String text) {
        String blockPrefix = getBlockPrefix(text);
        int startPosition = blockPrefix.length();
        processBlockText(text, startPosition);

        // Add text to enclosed element
        addTextToTop(currentBlockOpenedTags.peek(), text, text.length());

        // Add all enclosed elements to text
        while (!currentBlockOpenedTags.isEmpty()) {
            OpenedTag top = currentBlockOpenedTags.pop();

            Deque<SpanElement> topElementChildren = top.getChildren();
            topElementChildren.addFirst(new Text(top.getTag()));
            addElementsToTop(topElementChildren);
        }

        return createBlockElement(blockPrefix, currentBlockChildren);
    }

    private void processBlockText(String text, int startPosition) {
        // Start first text element
        currentBlockOpenedTags.push(new OpenedTag("", startPosition));

        boolean isMarkdownSymbolEscaped = false;
        for (int pos = startPosition; pos < text.length(); pos++) {
            // Skip escaped symbols
            if (isMarkdownSymbolEscaped) {
                isMarkdownSymbolEscaped = false;
                continue;
            }

            // Current char and char paired with next one substrings
            String single = String.valueOf(text.charAt(pos));
            String pair = text.substring(pos, pos < text.length() - 1 ? pos + 2 : pos);

            // Single char and pair statuses
            boolean pairIsTag = MD_TAGS.contains(pair);
            boolean singleIsEscaped = useHtmlEscaping && HTML_ESCAPING_CHARACTERS.contains(single);
            boolean singleIsTag = !pairIsTag && MD_TAGS.contains(single);
            isMarkdownSymbolEscaped = single.equals("\\");

            if (!singleIsEscaped && !singleIsTag && !pairIsTag && !isMarkdownSymbolEscaped) {
                continue;
            }

            // Close last text element, and add it to previous element
            addTextToTop(currentBlockOpenedTags.pop(), text, pos);

            // Skip one extra char for paired tag
            if (pairIsTag) {
                pos++;
            }

            if (singleIsEscaped) {
                // Add escaped char
                addElementToTop(createHtmlEscaped(single));
            } else if (!currentBlockOpenedTags.isEmpty() &&
                    ((currentBlockOpenedTags.peek().tagEquals(single) && singleIsTag) ||
                            (currentBlockOpenedTags.peek().tagEquals(pair))  && pairIsTag)) {
                // Add closed markdown element to previous element on stack
                OpenedTag top = currentBlockOpenedTags.pop();
                addElementToTop(createSpanElement(top.getTag(), new ArrayList<>(top.getChildren())));
            } else if (singleIsTag || pairIsTag) {
                // Start new element on top of the stack
                OpenedTag newOpenedTag = new OpenedTag(
                        singleIsTag ? single : pair, pos + 1
                );
                currentBlockOpenedTags.push(newOpenedTag);
            }

            // Start new text text
            currentBlockOpenedTags.push(new OpenedTag("", pos + 1));
        }
    }

    private void addTextToTop(OpenedTag textTag, String block, int pos) {
        String lastTextBlock = block.substring(textTag.getTextStartIndex(), pos);
        addElementToTop(new Text(lastTextBlock));
    }

    private void addElementToTop(SpanElement element) {
        if (!currentBlockOpenedTags.isEmpty()) {
            currentBlockOpenedTags.peek().addChild(element);
        } else {
            currentBlockChildren.add(element);
        }
    }

    private void addElementsToTop(Deque<SpanElement> elements) {
        if (!currentBlockOpenedTags.isEmpty()) {
            currentBlockOpenedTags.peek().addChildren(elements);
        } else {
            currentBlockChildren.addAll(elements);
        }
    }

    private static SpanElement createHtmlEscaped(String symbol) {
        return switch (symbol) {
            case "<" -> new Text("&lt;");
            case ">" -> new Text("&gt;");
            case "&" -> new Text("&amp;");
            default  -> new Text(symbol);
        };
    }

    private static SpanElement createSpanElement(String tag, List<SpanElement> children) {
        return switch (tag) {
            case "_", "*"   -> new Emphasis(children);
            case "__", "**" -> new Strong(children);
            case "--"       -> new Strikeout(children);
            case "`"        -> new Code(children);
            case "++"       -> new Underline(children);
            default -> throw new AssertionError("Unknown Span element tag.");
        };
    }

    private static BlockElement createBlockElement(String prefix, List<SpanElement> children) {
        if (prefix.startsWith("#")) {
            return new Header(prefix.length() - 1, children);
        }

        return new Paragraph(children);
    }

    private static String getBlockPrefix(String block) {
        if (block.startsWith("#")) {
            int i = 1;
            while (block.charAt(i) == '#') {
                i++;
            }

            if (block.charAt(i) == ' ') {
                return block.substring(0, i + 1);
            }
        }

        return "";
    }
}
