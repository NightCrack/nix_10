package ua.com.alevel.separator;

import java.math.BigInteger;
import java.util.Scanner;

public class Numbers {

    public void numbers() {
        Scanner inputStream = new Scanner(System.in);
        while(true) {
            System.out.print( System.lineSeparator() +
                    "This subprogram will parse input and compare all separated numbers." +
                    System.lineSeparator() +
                    "To return to the main menu input <back>." +
                    System.lineSeparator() +
                    "Your input: ");
            String input = inputStream.nextLine();
            if (input.equals("back")) break;
            if (input.isEmpty()) {
                System.out.println("Input is empty.");
            }
            else {
                String[] stringArray = input.replace("[^0-9]*", " ").split(" ");
                BigInteger[] bigIntegerArray = new BigInteger[stringArray.length];
                for ( int index = 0; index < stringArray.length; index++) {
                    bigIntegerArray[index] = new BigInteger(stringArray[index]);
                }
                for ( int index = 0; index < bigIntegerArray.length; index++) {
                    if (index == 0) {
                        continue;
                    } else {
                        String comparison = "";
                        switch ( bigIntegerArray[index-1].compareTo(bigIntegerArray[index])) {
                            case -1: {
                                comparison = " less than ";
                            }
                            case 0: {
                                comparison = " equal to ";
                            }
                            case 1: {
                                comparison = " greater than ";
                            }
                        }
                        System.out.println(bigIntegerArray[index-1] + comparison + bigIntegerArray[index]);
                    }
                }
            }
        }
    }
}
