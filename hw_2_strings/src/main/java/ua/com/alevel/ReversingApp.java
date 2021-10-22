package ua.com.alevel;

import java.util.Scanner;

public class ReversingApp {

    private static final String EXIT_VALUE = "q";
    private static final String RESOLVE_VALUE = "Resolve";

        public static void main(String args[]) {

            boolean state = true;

            do {
                String userInput = null;
                String criteria = null;
                String startCriteria = null;
                String endCriteria = null;
                boolean criteriaOpt = false;
                menu(0);
                menu(1);

                Scanner input = new Scanner(System.in);
                userInput = input.nextLine();

                if (!EXIT_VALUE.equals(userInput)) {

                    menu(2);
                    criteria = input.nextLine();
                    startCriteria = criteria;
                    if (!RESOLVE_VALUE.equals(criteria)) {

                        if (!EXIT_VALUE.equals(criteria)) {

                            menu(3);
                            endCriteria = input.nextLine();
                            if (!RESOLVE_VALUE.equals(endCriteria)) {

                                if (!EXIT_VALUE.equals(endCriteria)) {

                                    menu(4);
                                    String tempo = input.nextLine();
                                    criteriaOpt = Boolean.parseBoolean(tempo);
                                    if (!RESOLVE_VALUE.equals(tempo)) {

                                        if (!EXIT_VALUE.equals(tempo)) {

                                            System.out.println(System.lineSeparator() + ReverseString.reverse(userInput, startCriteria, endCriteria, criteriaOpt));
                                        } else
                                        {

                                            state = false;
                                        }
                                    } else
                                    {

                                        System.out.println(System.lineSeparator() + ReverseString.reverse(userInput, criteria));
                                    }
                                } else
                                {

                                    state = false;
                                }
                            } else
                            {

                                System.out.println(System.lineSeparator() + ReverseString.reverse(userInput, criteria));
                            }
                        } else
                        {

                            state = false;
                        }
                    } else
                    {

                        System.out.println(System.lineSeparator() + ReverseString.reverse(userInput));
                    }
                } else
                {

                    state = false;
                }
            } while(state);
        }

    private static void menu(int input) {
            switch (input) {

                case 1: System.out.print(System.lineSeparator() + "Your input: ");
                    break;
                case 2: System.out.print(System.lineSeparator() + "Your compartment (first or general) (criteria or index): ");
                    break;
                case 3: System.out.print(System.lineSeparator() + "Your last compartment criteria (or index): ");
                    break;
                case 4: System.out.print(System.lineSeparator() + "Are your criteria indexes (true or false; default - false)?: ");
                    break;
                default: System.out.print(System.lineSeparator() +
                        "Make your input (or input \""+ EXIT_VALUE + "\" to exit the program)." +
                        System.lineSeparator() +
                        "Print \"" + RESOLVE_VALUE + "\" to make reverse operation");
                    break;
            }

    }
}
