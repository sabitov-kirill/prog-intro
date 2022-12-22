package expression.parser;

import java.util.function.Supplier;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class BaseParser {
    private static final char END = '\0';
    private char ch = 0xffff;
    protected final CharSource source;
    protected final String sourceData;

    protected BaseParser(final String sourceData) {
        this.source = new StringSource(sourceData);
        this.sourceData = sourceData;
        take();
    }

    protected ParserUnexpectedCharException error(char expected) {
        return new ParserUnexpectedCharException(
                readableChar(expected),
                readableChar(ch),
                eof() ? source.getPosition() + 1 : source.getPosition(),
                eof() ? sourceData + ' ' : sourceData
        );
    }

    protected String readableChar(char ch) {
        return ch == END ? "EOF" : "'" + ch + "'";
    }

    protected char take() {
        final char result = ch;
        ch = source.hasNext() ? source.next() : END;
        return result;
    }

    protected boolean test(final char expected) {
        return ch == expected;
    }

    protected boolean test(final String expected) {
        boolean result = true;
        char anchorChar = ch;
        source.setAnchor();

        for (int i = 0; i < expected.length(); i++) {
            if (!test(expected.charAt(i))) {
                result = false;
                break;
            } else {
                take();
            }
        }

        ch = anchorChar;
        source.returnToAnchor();
        return result;
    }

    protected boolean take(final char expected) {
        if (test(expected)) {
            take();
            return true;
        }
        return false;
    }

    protected boolean take(final String expected) {
        char anchorChar = ch;
        source.setAnchor();

        for (int i = 0; i < expected.length(); i++) {
            if (!test(expected.charAt(i))) {
                ch = anchorChar;
                source.returnToAnchor();
                return false;
            } else {
                take();
            }
        }
        return true;
    }

    protected boolean takeWhitespace() {
        if (Character.isWhitespace(ch)) {
            take();
            return true;
        }
        return false;
    }

    protected void takeDigits(final StringBuilder sb) {
        while (between('0', '9')) {
            sb.append(take());
        }
    }

    protected boolean takeInteger(final StringBuilder sb) {
        if (take('-')) {
            sb.append('-');
        }
        if (take('0')) {
            sb.append('0');
        } else if (between('1', '9')) {
            takeDigits(sb);
        } else {
            return false;
        }
        return true;
    }

    protected void expect(final char expected) throws ParserUnexpectedCharException {
        if (!take(expected)) {
            throw error(expected);
        }
    }

    protected void expect(final String value) throws ParserUnexpectedCharException {
        for (final char c : value.toCharArray()) {
            expect(c);
        }
    }

    protected void expectWhitespace() throws ParserUnexpectedCharException {
        if (!takeWhitespace()) {
            throw error(' ');
        }
    }

    protected boolean eof() {
        return take(END);
    }

    protected boolean between(final char from, final char to) {
        return from <= ch && ch <= to;
    }

    protected void skipWhitespace() {
        while (takeWhitespace()) {
            // skip
        }
    }

    protected <T> T skipedWhitespace(Supplier<T> action) {
        skipWhitespace();
        T result = action.get();
        skipWhitespace();
        return result;
    }
}
