package markup;

public class Text implements SpanElement {
    private final String text;

    public Text(String text) {
        this.text = text;
    }

    private void printText(StringBuilder sb) {
        sb.append(text);
    }

    @Override
    public void toMarkdown(StringBuilder sb) {
        printText(sb);
    }

    @Override
    public void toHtml(StringBuilder sb) {
        printText(sb);
    }

    @Override
    public void toTex(StringBuilder sb) {
        printText(sb);
    }
}
