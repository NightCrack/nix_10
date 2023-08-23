package ua.com.nightcrack;

import ua.com.nightcrack.lib.StringReverse;

import java.util.Arrays;
import java.util.Scanner;

public class StringReversingApp {

    public static void main(String[] args) {
        Scanner inputStream = new Scanner(System.in);
        while (true) {
            String[] inputs = new String[3];
            // Reverse whole string
            // Reverse given pattern
            // Reverse given range
            for (int position = 0; position < 3; position++) {
                menu(position);
                inputs[position] = inputStream.nextLine();
                if (inputs[position].equals("Exit")) System.exit(0);
                if (position > 0 && inputs[position].equals("Resolve")) {
                    inputs = Arrays.copyOf(inputs, position);
                    break;
                }
            }
            System.out.println("Result: " + StringReverse.reverse(inputs));
        }
    }

    private static void menu(int number) {
        switch (number) {
            case 0 -> System.out.print("Your input (Input <Exit> to exit): ");
            case 1 -> System.out.print("Input start criteria or index (starting with \"#\")" +
                    System.lineSeparator() + "(input \"Resolve\" to reverse string on the given phase): ");
            case 2 -> System.out.print("Input end criteria or index (starting with \"#\")" +
                    System.lineSeparator() + "(input \"Resolve\" to reverse string on the given phase): ");
        }
    }
}