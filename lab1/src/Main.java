import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        try {
            long[][] matrix = {
                    {4, 8, 2},
                    {6, 3, 9},
                    {7, 1, 5}
            };

            long[][] transposedMatrix = transposeMatrix(matrix);

            System.out.println("Транспонована матриця:");
            printMatrix(transposedMatrix);

            long sumOfMinElements = calculateSumOfMinElements(transposedMatrix);

            System.out.println("Сума найменших елементів кожного стовпця: " + sumOfMinElements);
        } catch (Exception e) {
            System.err.println("Виникла помилка: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static long[][] transposeMatrix(long[][] matrix) {
        if (matrix == null || matrix.length == 0) {
            throw new IllegalArgumentException("Матриця не може бути порожньою");
        }

        int rows = matrix.length;
        int cols = matrix[0].length;
        long[][] transposed = new long[cols][rows];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                transposed[j][i] = matrix[i][j];
            }
        }

        return transposed;
    }

    public static long calculateSumOfMinElements(long[][] matrix) {
        if (matrix == null || matrix.length == 0) {
            throw new IllegalArgumentException("Матриця не може бути порожньою");
        }

        long sum = 0;

        for (int j = 0; j < matrix[0].length; j++) {
            long min = Long.MAX_VALUE;
            for (int i = 0; i < matrix.length; i++) {
                if (matrix[i][j] < min) {
                    min = matrix[i][j];
                }
            }
            sum += min;
        }

        return sum;
    }

    public static void printMatrix(long[][] matrix) {
        if (matrix == null || matrix.length == 0) {
            System.out.println("Матриця порожня");
            return;
        }

        for (long[] row : matrix) {
            System.out.println(Arrays.toString(row));
        }
    }
}
