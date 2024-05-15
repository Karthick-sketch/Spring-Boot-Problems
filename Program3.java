import java.util.Scanner;

public class Program3 {
    public static double[][] generateMatrix1(int ps) {
        double[][] matrix = new double[ps][ps];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                long rand;
                do {
                    rand = (long) (Math.random() * 1000);
                } while (rand < 100);

                matrix[i][j] = rand / 100.0;
            }
        }
        return matrix;
    }

    public static double[][] generateMatrix2(int ps) {
        double[][] matrix = new double[ps][ps];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                double rand;
                do {
                    rand = Math.random() * 10;
                } while (rand < 1);

                matrix[i][j] = rand;
            }
        }
        return matrix;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Please enter the perfect square number: ");
        double ps = Math.sqrt(scanner.nextInt());
        if (ps - (int) ps != 0) {
            System.out.println("Given number is not a perfect square");
        } else {
            // method 1
            // double[][] matrix = generateMatrix1((int) ps);

            // method 2
            double[][] matrix = generateMatrix2((int) ps);

            for (double[] innerArray : matrix) {
                for (double val : innerArray) {
                    System.out.printf("%.2f ", val);
                }
                System.out.println();
            }
        }
    }
}
