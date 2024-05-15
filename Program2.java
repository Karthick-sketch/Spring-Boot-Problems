import java.util.Scanner;

public class Program2 {

    public static long sum(int[][] input) {
        //your implementation here
        int diagonalSum = 0, n = input.length;
        for (int i = 0, j = n-1; i < n; i++, j--) {
            diagonalSum += (int) Math.pow(input[i][i], n);
            diagonalSum += (int) Math.pow(input[i][j], n);
        }
        return diagonalSum;
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Please enter the n for matrix nxn: ");
            int n = scanner.nextInt();
            scanner.nextLine();
            int[][] input = new int[n][n];
            System.out.println("Please enter matrix values (in matrix format):");
            //read your input matrix here
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    input[i][j] = scanner.nextInt();
                }
            }
            System.out.println("Total sum is: " + sum(input));
        }
    }
}
