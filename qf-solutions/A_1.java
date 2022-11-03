import java.util.Scanner;

public class ProblemA {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int
                a = in.nextInt(),
                b = in.nextInt(),
                n = in.nextInt(),
                res = (int) Math.ceil(((double) n - b) / (b - a)) * 2 + 1;

        System.out.println(res);
    }
}
