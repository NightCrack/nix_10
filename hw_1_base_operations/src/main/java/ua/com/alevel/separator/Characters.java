package ua.com.alevel.separator;

import java.util.*;

public class Characters {

    public void characters() {

        int exit = 0;
        do {

            menu();
            Scanner buffer = new Scanner(System.in);
            String userInput = buffer.nextLine();
            switch (userInput) {

                case "Q", "q" -> exit = 1;
            }
            char[] inputCharArray = userInput.toCharArray();
            Arrays.sort(inputCharArray);
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
        } while (1 != exit);
    }

    private static void menu() {

        System.out.print( System.lineSeparator() +
                "Make your input and press \"Enter\". Input \"Q\" (or \"q\") to exit." +
                System.lineSeparator() + "Your input: ");
    }
}