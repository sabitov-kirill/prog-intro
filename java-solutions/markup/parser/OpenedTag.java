package markup.parser;

import markup.SpanElement;

import java.util.ArrayDeque;
import java.util.Deque;

class OpenedTag {
    private final Deque<SpanElement> children = new ArrayDeque<>();
    private final String tag;
    private int textStartIndex;

    public OpenedTag(String tag, int startIndex) {
        this.tag = tag;
        this.textStartIndex = startIndex;
    }

    public OpenedTag addChild(SpanElement child) {
        children.add(child);
        return this;
    }

    public OpenedTag addChildren(Deque<SpanElement> children) {
        this.children.addAll(children);
        return this;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof OpenedTag otherTagOccurrence) {
            return tag.equals(otherTagOccurrence.tag);
        }
        return false;
    }

    public boolean tagEquals(String tag) {
        return this.tag.equals(tag);
    }

    public Deque<SpanElement> getChildren() {
        return children;
    }

    public String getTag() {
        return tag;
    }

    public int getTextStartIndex() {
        return textStartIndex;
    }

    public OpenedTag setTextStartIndex(int textStartIndex) {
        this.textStartIndex = textStartIndex;
        return this;
    }
}
