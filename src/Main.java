import java.util.concurrent.TimeUnit;

class MatrixMultiplier {
    private static final int MATRIX_SIZE = 3;

    // Função para multiplicação matricial
    static void multiplyMatrix(float[][] A, float[][] B, float[][] result, int startRow, int endRow) {
        for (int i = startRow; i < endRow; i++) {
            for (int j = 0; j < MATRIX_SIZE; j++) {
                result[i][j] = 0;
                for (int k = 0; k < MATRIX_SIZE; k++) {
                    result[i][j] += A[i][k] * B[k][j];
                }
            }
        }
    }

    public static void main(String[] args) {
        float[][] matrixA = {{1.0f, 2.0f, 3.0f}, {4.0f, 5.0f, 6.0f}, {7.0f, 8.0f, 9.0f}};
        float[][] matrixB = {{9.0f, 8.0f, 7.0f}, {6.0f, 5.0f, 4.0f}, {3.0f, 2.0f, 1.0f}};
        float[][] result = new float[MATRIX_SIZE][MATRIX_SIZE];

        // Medir o tempo para multiplicação matricial single-thread
        long startTime = System.nanoTime();
        multiplyMatrix(matrixA, matrixB, result, 0, MATRIX_SIZE);
        long endTime = System.nanoTime();
        double elapsed_time_single = TimeUnit.NANOSECONDS.toMillis(endTime - startTime) / 1000.0;

        System.out.println("Multiplicação Matricial Single-thread:");
        for (int i = 0; i < MATRIX_SIZE; i++) {
            for (int j = 0; j < MATRIX_SIZE; j++) {
                System.out.printf("%.2f ", result[i][j]);
            }
            System.out.println();
        }

        System.out.printf("Tempo de execução da multiplicação matricial single-thread: %.4f segundos\n", elapsed_time_single);

        // Medir o tempo para multiplicação matricial multithread
        Thread[] threads = new Thread[2];
        int mid = MATRIX_SIZE / 2;
        float[][] multiThreadResult = new float[MATRIX_SIZE][MATRIX_SIZE];

        Runnable multiplyTask1 = () -> {
            multiplyMatrix(matrixA, matrixB, multiThreadResult, 0, mid);
        };

        Runnable multiplyTask2 = () -> {
            multiplyMatrix(matrixA, matrixB, multiThreadResult, mid, MATRIX_SIZE);
        };

        threads[0] = new Thread(multiplyTask1);
        threads[1] = new Thread(multiplyTask2);

        startTime = System.nanoTime();

        for (int i = 0; i < 2; i++) {
            threads[i].start();
        }

        for (int i = 0; i < 2; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        endTime = System.nanoTime();
        double elapsed_time_multithread = TimeUnit.NANOSECONDS.toMillis(endTime - startTime) / 1000.0;

        System.out.println("\nMultiplicação Matricial Multithread:");
        for (int i = 0; i < MATRIX_SIZE; i++) {
            for (int j = 0; j < MATRIX_SIZE; j++) {
                System.out.printf("%.2f ", multiThreadResult[i][j]);
            }
            System.out.println();
        }

        System.out.printf("Tempo de execução da multiplicação matricial multithread: %.4f segundos\n", elapsed_time_multithread);
    }
}