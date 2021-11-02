package ua.com.alevel.level2;

import ua.com.alevel.level2.util.ParenthesisChecker;

import java.util.Objects;
import java.util.Scanner;

public class CheckParenthesis {

    private final Scanner userInput = new Scanner(System.in);
    private final String EXIT_VALUE = "0";
    private String EVALUATOR = "1";

    public void check() {
        System.out.println();
        System.out.println("Input string to validate. Input "
                + EXIT_VALUE + " to exit the sub-level.");
        do {
            String returnMessage = "";
            System.out.print("Your input: ");
            String input = userInput.nextLine();
            if (input.isBlank()) {
                returnMessage = "Input is blank";
            } else {
                if (input.equals(EXIT_VALUE)) {
                    EVALUATOR = EXIT_VALUE;
                } else {
                    do {
                        int[] openedParenthesis = ParenthesisChecker.lastStartParenthesisIndex(input);
                        if (openedParenthesis[1] == -1) {
                            returnMessage = "String is allowable";
                        } else {
                            int closingParenthesisIndex = ParenthesisChecker.nearestEndParenthesisIndex(input, openedParenthesis);
                            if (closingParenthesisIndex == -1) {
                                returnMessage = "String is not allowable";
                            } else {
                                input = ParenthesisChecker.cutOutSubstring(input, openedParenthesis[1], closingParenthesisIndex);
                            }
                        }
                    } while (returnMessage.equals(""));
                }
            }
            System.out.println(returnMessage);
        } while (!Objects.equals(EVALUATOR, EXIT_VALUE));
    }
}
