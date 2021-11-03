package ua.com.alevel.level1;

import ua.com.alevel.level1.entity.Dot;
import ua.com.alevel.level1.util.AreaOfTriangular;

import java.util.Scanner;

public class TriangularArea {

    private final Scanner userInput = new Scanner(System.in);
    private final String EXIT_VALUE = "0";
    private String EVALUATOR = "1";

    public void check() {
        System.out.println();
        System.out.println("Input coordinates of three dots that forms the trident which area must be calculated.");
        System.out.println("Input format is:");
        System.out.println("*Dot1* 'coordinateX';'coordinateY' whitespace");
        System.out.println("*Dot2* 'coordinateX';'coordinateY' whitespace");
        System.out.println("*Dot3* 'coordinateX';'coordinateY'. Input "
                + EXIT_VALUE + " to exit sub-level.");
        do {
            System.out.print("Your input: ");
            String input = userInput.nextLine();
            if (input.isBlank()) {
                System.out.println("Your input is blank.");
            } else {
                if (input.equals(EXIT_VALUE)) {
                    EVALUATOR = EXIT_VALUE;
                } else {
                    String[] rivenDots = input.split(" ");
                    if (rivenDots.length != 3) {
                        System.out.println("Wrong dots' amount. It must be 3 dots.");
                    } else {
                        Dot[] dotsArray = new Dot[3];
                        for (int index = 0; index < rivenDots.length; index++) {
                            String[] rivenDotCoordinates = rivenDots[index].split(";");
                            if (rivenDotCoordinates.length != 2) {
                                System.out.println("Incorrect coordinates of dot" + (index + 1));
                            } else {
                                dotsArray[index] = dotCheck(rivenDotCoordinates);
                            }
                        }
                        if (dotsArray[0] != null && dotsArray[1] != null && dotsArray[2] != null) {
                            System.out.println("The area is: " + AreaOfTriangular.getArea(dotsArray));
                        }
                    }
                }
            }
        } while (EVALUATOR != EXIT_VALUE);
    }

    private Dot dotCheck(String partX, String partY) {
        if (checkIfNumber(partX) && checkIfNumber(partY)) {
            Dot dot = new Dot();
            dot.setCoordinateX(Integer.parseInt(partX));
            dot.setCoordinateY(Integer.parseInt(partY));
            return dot;
        } else {
            System.out.println("Incorrect coordinate parameter " +
                    "(must be in 'integer number' format).");
            return null;
        }
    }

    private Dot dotCheck(String[] parts) {
        return dotCheck(parts[0], parts[1]);
    }

    private boolean checkIfNumber(String userInput) {
        char[] inputCharArray = userInput.toCharArray();
        int[] transferArray = new int[inputCharArray.length];
        int numberOfElements = 0;
        for (int i = 0; i < inputCharArray.length; i++) {
            if (inputCharArray[i] <= '9' && inputCharArray[i] >= '0') {
                transferArray[i] = Character.getNumericValue(inputCharArray[i]) + 1;
                numberOfElements++;
            } else {
                transferArray[i] = 0;
            }
        }
        int arraySum = 0;
        for (int i = 0; i < transferArray.length; i++) {
            arraySum += transferArray[i];
        }
        if (arraySum != 0 && (numberOfElements == inputCharArray.length)) {
            return true;
        } else {
            return false;
        }
    }
}
