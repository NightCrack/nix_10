package ua.com.alevel.level1;

import java.util.Arrays;
import java.util.Scanner;

public class UniqueNumberCount {

    private final Scanner userInput = new Scanner(System.in);
    private final String REGEX = "[^0-9]+";
    private final String EXIT_VALUE = "0";
    private String EVALUATOR = null;

    public void check() {
        System.out.println();
        System.out.println("Make your input. Input "
                + EXIT_VALUE + " to exit sub-level.");
        do {
            System.out.println();
            System.out.print("Your input: ");
            String inputValue = userInput.nextLine();
                String[] stringArray = inputValue.split(REGEX);
                if (!inputValue.isBlank()) {
                    if (!inputValue.equals(EXIT_VALUE)){
                        if ((stringArray.length == 1 && !stringArray[0].isBlank()) || (stringArray.length > 1 )) {
                            if (stringArray[0].isBlank()) {
                                System.arraycopy(stringArray, 1, stringArray, 0, (stringArray.length - 1));
                            }
                            int[] intArray = new int[stringArray.length];
                            for (int index = 0; index < stringArray.length; index++) {
                                intArray[index] = Integer.parseInt(stringArray[index]);
                            }
                            Arrays.sort(intArray);
                            int numberAmount = 1;
                            for (int index = 1; index < intArray.length; index++) {
                                if (intArray[index] != intArray[index - 1]) {
                                    numberAmount++;
                                }
                            }
                            System.out.println("Amount of unique numbers: " + numberAmount);
                        } else {
                            System.out.println("Your input isn't containing numbers.");
                        }
                    } else {
                        EVALUATOR = EXIT_VALUE;
                    }

                } else {
                    System.out.println("Your input isn't containing numbers.");
                }
        } while (EVALUATOR != EXIT_VALUE);
    }
}
