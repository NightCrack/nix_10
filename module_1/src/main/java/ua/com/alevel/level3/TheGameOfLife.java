package ua.com.alevel.level3;

import ua.com.alevel.level3.Service.CustomServices;
import ua.com.alevel.level3.util.CustomUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class TheGameOfLife {

    private final Scanner userInput = new Scanner(System.in);
    private final String EXIT_VALUE = "0";
    private final int SECOND = 1000;
    private final int DELAY_MILLISECONDS_VALUE = 250;
    private String EVALUATOR = "1";

    public void run() throws InterruptedException, IOException {
        do {
            System.out.println();
            System.out.println("Input '"
                    + EXIT_VALUE + "' to exit the sub-level.");
            System.out.println("Input field dimension values and system's lifetime in the following format:");
            System.out.println("'field height' whitespace 'field width' whitespace 'lifetime(sec)'.");
            System.out.print("Your input: ");
            String input = userInput.nextLine();
            if (input.isBlank()) {
                System.out.print("Input is blank.");
            } else {
                if (input.equals(EXIT_VALUE)) {
                    EVALUATOR = EXIT_VALUE;
                } else {
                    String[] inputArray = input.split(" ");
                    if (inputArray.length != 3 || !CustomUtils.checkIfNumber(inputArray[0] + inputArray[1] + inputArray[2]) ||
                            inputArray[0].equals("0") || inputArray[1].equals("0") || inputArray[2].equals("0")) {
                        System.out.println("Incorrect input.");
                    } else {
                        int[][] baseMatrix = CustomUtils.buildRandom(Integer.parseInt(inputArray[0]), Integer.parseInt(inputArray[1]));
                        long startTime = System.currentTimeMillis();
                        long userTimeInput = (long) Integer.parseInt(inputArray[2]) * SECOND;
                        while (System.currentTimeMillis() < (startTime + userTimeInput)) {
                            int[][] resultMatrix = CustomUtils.nextGen(baseMatrix);
                            baseMatrix = Arrays.copyOf(resultMatrix, resultMatrix.length);
                            TimeUnit.MILLISECONDS.sleep(DELAY_MILLISECONDS_VALUE);
                            CustomServices.clearConsole();
                            CustomServices.printMatrix(resultMatrix);
                        }
                    }
                }
            }
        } while (!EVALUATOR.equals(EXIT_VALUE));
    }
}
