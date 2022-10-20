package markup;

import java.util.List;
import java.util.function.Consumer;

public class MarkupPersonalTest {
    private static void testElementMarkupImpl(String expectedMarkup, String markupName, Consumer<StringBuilder> toMarkupMethod) {
        StringBuilder sb = new StringBuilder();
        toMarkupMethod.accept(sb);
        if (!sb.toString().equals(expectedMarkup)) {
            System.err.println(
                    "Testing element " + markupName + " failed\n" +
                            "Expected: " + expectedMarkup + "\n" +
                            "Actual  : " + sb + "\n\n"
            );
        } else {
            System.out.println(
                    "Tested element " + markupName + " successfully.\n" +
                            "Result: " + expectedMarkup + "\n\n"
            );
        }
    }

    private static void testElementMarkdown(String expectedMarkup, Element el) {
        testElementMarkupImpl(expectedMarkup, "Markdown", el::toMarkdown);
    }

    private static void testElementHtml(String expectedMarkup, Element el) {
        testElementMarkupImpl(expectedMarkup, "Html", el::toHtml);
    }

    public static void main(String[] args) {
        testElementMarkdown("1", new Text("1"));
        testElementMarkdown("Hello!Hello!", new Paragraph(
                List.of(new Text("Hello!"), new Text("Hello!"))
        ));
        testElementMarkdown("   1   ", new Paragraph(
                List.of(new Text("   "), new Text("1 "), new Text("  "))
        ));

        testElementMarkdown("*bold*", new Emphasis(List.of(new Text("bold"))));
        testElementMarkdown("__strong bold__", new Strong(List.of(new Text("strong bold"))));
        testElementMarkdown("~struck out~", new Strikeout(List.of(new Text("struck out"))));

        testElementMarkdown("__1~2*34*5~6__", new Paragraph(List.of(
                new Strong(List.of(
                        new Text("1"),
                        new Strikeout(List.of(
                                new Text("2"),
                                new Emphasis(List.of(
                                        new Text("3"),
                                        new Text("4")
                                )),
                                new Text("5")
                        )),
                        new Text("6")
                ))
        )));

        testElementHtml("<s>asd</s>", new Strikeout(List.of(new Text("asd"))));
        testElementHtml("<strong>1<s>2<em>34</em>5</s>6</strong>", new Strong(List.of(
                new Text("1"),
                new Strikeout(List.of(
                        new Text("2"),
                        new Emphasis(List.of(
                                new Text("3"),
                                new Text("4")
                        )),
                        new Text("5")
                )),
                new Text("6")
        )));
    }
}
