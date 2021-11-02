package ua.com.alevel;

import ua.com.alevel.level1.HorseMoveCheck;
import ua.com.alevel.level1.TriangularArea;
import ua.com.alevel.level1.UniqueNumberCount;
import ua.com.alevel.level2.BinaryTreeDepth;
import ua.com.alevel.level2.CheckParenthesis;
import ua.com.alevel.level3.TheGameOfLife;

import java.util.Scanner;

public class Module1Main {

    private static final Scanner userInput = new Scanner(System.in);
    private static final int EXIT_VALUE = 0;
    private static int EVALUATOR = 1;

    public static void main(String[] args) {
        do {
            screenTips();
        } while (EVALUATOR != EXIT_VALUE);
    }

    private static void screenTips() {
        System.out.println();
        System.out.println("Choose your level (from 1 to 3), or type "
                + EXIT_VALUE + " to exit the program.");
        System.out.print("Your input: ");
        switch(userInput.nextLine()) {
            case "1": level1();
                break;
            case "2": level2();
                break;
            case "3": level3();
                break;
            case "0": EVALUATOR = EXIT_VALUE;
                break;
            default: System.out.println();
                System.out.println("Wrong input.");
                break;
        }
    }

    private static void level1() {
        System.out.println();
        System.out.println("Choose the task: ");
        System.out.println("(1) Count unique numbers");
        System.out.println("(2) Check horse's move");
        System.out.println("(3) Calculate an area of a triangular");
        System.out.println("(" + EXIT_VALUE + ") Exit level");
        System.out.print("Your input: ");
        do {
            switch (userInput.nextLine()) {
                case "1":
                    new UniqueNumberCount().check();
                    EVALUATOR = EXIT_VALUE;
                    break;
                case "2":
                    new HorseMoveCheck().check();
                    EVALUATOR = EXIT_VALUE;
                    break;
                case "3":
                    new TriangularArea().check();
                    EVALUATOR = EXIT_VALUE;
                    break;
                case "0": EVALUATOR = EXIT_VALUE;
                    break;
                default: System.out.println();
                    System.out.println("Wrong input.");
                    System.out.print("Your input:");
                    break;
            }
        } while (EVALUATOR != EXIT_VALUE);
        EVALUATOR = 1;
    }

    private static void level2() {
        System.out.println();
        System.out.println("Choose the task: ");
        System.out.println("(1) String validation");
        System.out.println("(2) Generate and seize random tree");
        System.out.println("(" + EXIT_VALUE + ") Exit level");
        System.out.print("Your input: ");
        do {
            switch (userInput.nextLine()) {
                case "1":
                    new CheckParenthesis().check();
                    EVALUATOR = EXIT_VALUE;
                    break;
                case "2":
                    new BinaryTreeDepth().check();
                    EVALUATOR = EXIT_VALUE;
                    break;
                case "0": EVALUATOR = EXIT_VALUE;
                    break;
                default: System.out.println();
                    System.out.println("Wrong input.");
                    System.out.print("Your input:");
                    break;
            }
        } while (EVALUATOR != EXIT_VALUE);
        EVALUATOR = 1;
    }

    private static void level3() {
        new TheGameOfLife().run();
    }


}
