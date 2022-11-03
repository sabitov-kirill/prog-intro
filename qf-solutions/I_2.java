import java.util.Scanner;

public class ProblemI {
    private record Obelisk(int x, int y, int h) { }

    private Obelisk[] obelisks;
    private int n;
    private int x, y, h;

    public void readInput() {
        Scanner in = new Scanner(System.in);

        n = in.nextInt();
        obelisks = new Obelisk[n];
        for (int i = 0; i < n; i++) {
            obelisks[i] = new Obelisk(
                    in.nextInt(),
                    in.nextInt(),
                    in.nextInt()
            );
        }
    }

    public void solve() {
        int x_l = Integer.MAX_VALUE, x_r = Integer.MIN_VALUE;
        int y_l = Integer.MAX_VALUE, y_r = Integer.MIN_VALUE;

        for (Obelisk ob : obelisks) {
            x_l = Math.min(x_l, ob.x() - ob.h());
            x_r = Math.max(x_r, ob.x() + ob.h());
            y_l = Math.min(y_l, ob.y() - ob.h());
            y_r = Math.max(y_r, ob.y() + ob.h());
        }

        x = (x_l + x_r) / 2;
        y = (y_l + y_r) / 2;
        h = (int) Math.ceil((float) Math.max(x_r - x_l, y_r - y_l) / 2);
    }

    public void output() {
        System.out.println(x + " " + y + " " + h);
    }

    public static void main(String[] args) {
        ProblemI pr = new ProblemI();
        pr.readInput();
        pr.solve();
        pr.output();
    }
}
