package markup;

public class Test {
    public static void main(String[] args) {
        OrderedList list = new OrderedList(
                new ListItem(new Paragraph("List item 1")),
                new ListItem(new Paragraph(new Emphasis("Emphasized Li 2"), new Text("Simple text")))
        );

        StringBuilder sb = new StringBuilder();
        list.toHtml(sb);
        System.out.println(sb);

        sb.setLength(0);
        list.toBBCode(sb);
        System.out.println(sb);
    }
}
