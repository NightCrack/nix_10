package ua.com.alevel.separator;

import java.util.Arrays;
import java.util.Scanner;

public class Numbers {
    public void numbers () {

        int exit = 0;
       do {

            menu();
            Scanner buffer = new Scanner(System.in);
            String userInput = buffer.nextLine();
           switch (userInput) {

               case "Q", "q" -> exit = 1;
           }
            char[] inputCharArray = userInput.toCharArray();
                int[] transferArray = new int[inputCharArray.length];
                for (int i = 0; i < inputCharArray.length; i++) {

                    if (inputCharArray[i] <= '9' && inputCharArray[i] >= '0') {

                        transferArray[i] = Character.getNumericValue(inputCharArray[i]) + 1;
                    } else {

                        transferArray[i] = 0;
                    }
                }
                int size = 1, finalElementValue = 0, coefficient, arraySum = 0;
                int[] reconstructedNumbersArray = new int[size];
                for(int i = 0; i < transferArray.length; i++) {

                    arraySum += transferArray[i];
                }
                if (arraySum == 0) {

                    System.out.println( System.lineSeparator() +
                                        "No numbers in input string" + System.lineSeparator());
                    menu();
                } else {

                    int[] reversedArray = new int[transferArray.length];
                    for (int i = 0; i < transferArray.length; i++) {

                        reversedArray[i] = transferArray[transferArray.length - i - 1];
                    }
                    for (int i = 0, multiplier = 0; i < reversedArray.length; i++) {

                        if (reversedArray[i] != 0) {

                            ++multiplier;
                            int j;
                            for (j = 0, coefficient = 1; j < (multiplier - 1); j++) {

                                coefficient *= 10;
                            }
                            finalElementValue += ((reversedArray[i] - 1) * coefficient);
                        } else if (finalElementValue != 0) {

                            reconstructedNumbersArray = Arrays.copyOf(reconstructedNumbersArray, size++);
                            reconstructedNumbersArray[size - 2] = finalElementValue;
                            finalElementValue = 0;
                            multiplier = 0;
                        }
                        if (reversedArray[i] == 1) {

                            if (i == reversedArray.length - 1) {

                                reconstructedNumbersArray = Arrays.copyOf(reconstructedNumbersArray, size++);
                                reconstructedNumbersArray[size - 2] = finalElementValue;
                                finalElementValue = 0;
                                multiplier = 0;
                            } else if (reversedArray[i+1] == 0) {

                                reconstructedNumbersArray = Arrays.copyOf(reconstructedNumbersArray, size++);
                                reconstructedNumbersArray[size - 2] = finalElementValue;
                                finalElementValue = 0;
                                multiplier = 0;
                            }
                        }
                    }
                }
                if (finalElementValue != 0) {

                    reconstructedNumbersArray = Arrays.copyOf(reconstructedNumbersArray, size++);
                    reconstructedNumbersArray[size - 2] = finalElementValue;
                }
                int[] finalArray = new int[reconstructedNumbersArray.length];
                for (int i = 0; i < reconstructedNumbersArray.length; i++) {

                    finalArray[i] = reconstructedNumbersArray[reconstructedNumbersArray.length - i - 1];
                }
                int finalArraySum = 0;
                for (int i = 0; i < finalArray.length; i++) {

                    finalArraySum += finalArray[i];
                }
                System.out.println( System.lineSeparator() + "Sum of numbers equals to: " + finalArraySum);
        } while (exit != 1);
    }
    private static void menu() {

        System.out.print( System.lineSeparator() +
                            "Make your input and press \"Enter\". Input \"Q\" (or \"q\") to exit." +
                            System.lineSeparator() + "Your input: " );
    }
}