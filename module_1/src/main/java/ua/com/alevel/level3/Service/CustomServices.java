package ua.com.alevel.level3.Service;

import java.io.IOException;

public class CustomServices {

    public static void printMatrix(int[][] matrix) {
        for (int indexA = 0; indexA < matrix.length; indexA++) {
            for (int indexB = 0; indexB < matrix[indexA].length; indexB++) {
                if (matrix[indexA][indexB] == 1) {
                    System.out.print("*");
                } else {
                    System.out.print(matrix[indexA][indexB]);
                }
            }
            System.out.println();
        }
    }

    public static void clearConsole() throws IOException, InterruptedException {
        final String os = System.getProperty("os.name");
        String command = null;
        switch (os) {
            case "Windows":
                command = "cls";
                break;
            default:
                command = "clear";
        }
        new ProcessBuilder(command).inheritIO().start().waitFor();
    }
}
