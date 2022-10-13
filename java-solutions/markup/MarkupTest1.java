package markup;

import java.util.List;

public class MarkupTest1 {
    private static void testElementMarkup(String expectedMarkup, Element el) {
        StringBuilder sb = new StringBuilder();
        el.toMarkdown(sb);
        if (!sb.toString().equals(expectedMarkup)) {
            System.err.println(
                    "Testing element markup failed\n" +
                    "Expected: " + expectedMarkup + "\n" +
                    "Actual  : " + sb + "\n\n"
            );
        } else {
            System.out.println(
                    "Tested element markup successfully.\n" +
                    "Result: " + expectedMarkup + "\n\n"
            );
        }
    }

    public static void main(String[] args) {
        testElementMarkup("1", new Text("1"));
        testElementMarkup("Hello!Hello!", new Paragraph(
                List.of(new Text("Hello!"), new Text("Hello!"))
        ));
        testElementMarkup("   1   ", new Paragraph(
                List.of(new Text("   "), new Text("1 "), new Text("  "))
        ));

        testElementMarkup("*bold*", new Emphasis(List.of(new Text("bold"))));
        testElementMarkup("__strong bold__", new Strong(List.of(new Text("strong bold"))));
        testElementMarkup("~struck out~", new Strikeout(List.of(new Text("struck out"))));

        testElementMarkup("__1~2*34*5~6__", new Paragraph(List.of(
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
    }
}
