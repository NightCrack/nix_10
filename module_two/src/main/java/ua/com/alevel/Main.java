package ua.com.alevel;

import ua.com.alevel.daterefactor.DateRefactor;
import ua.com.alevel.routing.Routing;
import ua.com.alevel.uniquename.UniqueName;

import java.util.Scanner;

public class Main {

    private static final String EXIT_VALUE = "exit";
    private static final String HELP = "help";
    private static final String INPUT = "input";
    private static final String ONE = "1";
    private static final String TWO = "2";
    private static final String THREE = "3";
    private static final Scanner userInput = new Scanner(System.in);

    public static void main(String[] args) {
        opt(HELP);
        while (true) {
            opt(INPUT);
            opt(userInput.nextLine());
        }
    }

    private static void opt(String input) {
        switch (input) {
            case HELP -> {
                System.out.println();
                System.out.println("Choose the program:");
                System.out.println("Date check & refactor - print <" + ONE + ">");
                System.out.println("Find first unique name - print <" + TWO + ">");
                System.out.println("Route between cities - print <" + THREE + ">");
                System.out.println("To exit - print <" + EXIT_VALUE + ">");
                System.out.println();
            }
            case INPUT -> System.out.print("Your input: ");
            case ONE -> {
                new DateRefactor().run();
                opt(HELP);
            }
            case TWO -> {
                new UniqueName().run();
                opt(HELP);
            }
            case THREE -> {
                new Routing().run();
                opt(HELP);
            }
            case EXIT_VALUE -> System.exit(0);
            default -> {
                System.out.println();
                System.out.println("Wrong input");
                System.out.println();
            }
        }
    }
}
