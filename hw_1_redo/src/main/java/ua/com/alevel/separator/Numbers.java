package ua.com.alevel.separator;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Scanner;

public class Numbers {

    public void numbers() {
        Scanner inputStream = new Scanner(System.in);
        while (true) {
            System.out.print(System.lineSeparator() +
                    "This subprogram will parse input and compare all separated numbers." +
                    System.lineSeparator() +
                    "To return to the main menu input <back>." +
                    System.lineSeparator() +
                    "Your input: ");
            String input = inputStream.nextLine();
            if (input.equals("back")) break;
            if (input.isEmpty()) {
                System.out.println("Input is empty.");
            } else {
                String[] stringArray = input
                        .split("[^0-9]+");
                if (stringArray.length == 0) continue;
                if (stringArray[0].isBlank()) {
                    stringArray = Arrays.copyOfRange(stringArray, 1, stringArray.length);
                }
                BigInteger[] bigIntegerArray = new BigInteger[stringArray.length];
                for (int index = 0; index < stringArray.length; index++) {
                    bigIntegerArray[index] = new BigInteger(stringArray[index]);
                }
                for (int index = 0; index < bigIntegerArray.length; index++) {
                    if (index != 0) {
                        String comparison = getComparison(bigIntegerArray, index);
                        System.out.println(bigIntegerArray[index - 1] + comparison + bigIntegerArray[index]);
                    }
                }
            }
        }
    }

    private String getComparison(BigInteger[] bigIntegerArray, int index) {
        var comparison = "";
        switch (bigIntegerArray[index - 1].compareTo(bigIntegerArray[index])) {
            case -1 -> comparison = " less than ";
            case 0 -> comparison = " equal to ";
            case 1 -> comparison = " greater than ";
        }
        return comparison;
    }
}
