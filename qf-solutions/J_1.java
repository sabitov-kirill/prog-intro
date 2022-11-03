import java.util.Scanner;

public class ProblemJ {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int[][] a = new int[n][n];
        int[][] res = new int[n][n];

        in.nextLine();
        for (int i = 0; i < n; i++) {
            String line = in.nextLine();
            for (int j = 0; j < n; j++) {
                a[i][j] = Character.getNumericValue(line.charAt(j));
            }
        }

        for (int i = 0; i < n; ++i) {
            for (int j = i + 1; j < n; ++j) {
                int paths = 0;
                for (int k = i + 1; k < j; ++k) {
                    if (res[i][k] > 0) {
                        paths += a[k][j];
                    }
                }

                if ((paths + 1) % 10 == a[i][j]) {
                    res[i][j] = 1;
                }
            }
        }

        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                System.out.print(res[i][j]);
            }
            System.out.println();
        }
    }
}
