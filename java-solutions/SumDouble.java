public class SumDouble {
    public static void main(String[] args) {
        String text = String.join(" ", args) + " ";

        double sum = 0;
        int numberStart = 0;
        boolean isPrevCharWhitespace = true;
        for (int index = 0; index < text.length(); index++) {
            char ch = text.charAt(index);
            boolean isChWhitespace = Character.isWhitespace(ch);

            if (!isChWhitespace && isPrevCharWhitespace) {
                numberStart = index;
                isPrevCharWhitespace = false;
            }
            if (isChWhitespace && !isPrevCharWhitespace) {
                sum += Double.valueOf(text.substring(numberStart, index));
                isPrevCharWhitespace = true;
            }
        }

        System.out.println(sum);
    }
}
