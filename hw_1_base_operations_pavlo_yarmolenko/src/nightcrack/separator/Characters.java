package nightcrack.separator;

import java.util.*;

public class Characters {
    public void characters() {
        int exit = 0;
        do {
            menu();
            Scanner buffer = new Scanner(System.in);
            String userInput = buffer.nextLine();
            switch (userInput) {
                case "Q":
                case "q":
                    exit = 1;
                    break;
            }
            char[] inputCharArray = userInput.toCharArray();
            Arrays.sort(inputCharArray);
            /* for (int i = 0; i < inputCharArray.length; i++) {
                System.out.print(inputCharArray[i] + " ");
            }
            System.out.println(); */
            int symbolCount = 1;
            for (int i = 1; i < inputCharArray.length; i++) {
                if (inputCharArray[i - 1] != inputCharArray[i]) {
                    System.out.println(inputCharArray[i - 1] + ": " + symbolCount);
                    symbolCount = 1;
                } else {
                    symbolCount++;
                }
            }
            System.out.println(inputCharArray[inputCharArray.length - 1] + ": " + symbolCount);
        } while (exit != 1);
    }

    private static void menu() {
        System.out.print( System.lineSeparator() +
                "Make your input and press \"Enter\". Input \"Q\" (or \"q\") to exit." +
                System.lineSeparator() + "Your input: ");
    }
}