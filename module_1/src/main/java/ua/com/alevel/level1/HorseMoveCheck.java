package ua.com.alevel.level1;


import ua.com.alevel.level1.entity.HorseFigure;
import ua.com.alevel.level1.util.CoordinateEncoder;

import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HorseMoveCheck {

    private final Scanner userInput = new Scanner(System.in);
    private final String REGEX_NUMERICAL = "[0-9]+";
    private final String REGEX_ALPHABETICAL = "[a-zA-Z]+";
    private final String EXIT_VALUE = "0";
    private String EVALUATOR = "1";

    public void check() {
        HorseFigure currentPosition = null;
        System.out.println();
        System.out.println("Input ♘(a horse) position. Print "
                + EXIT_VALUE + " to exit sub-level.");
        System.out.println();
        System.out.print("Your input: ");
        do {
            String input = userInput.nextLine();
            if (input.equals(EXIT_VALUE)) {
                EVALUATOR = EXIT_VALUE;
            } else {
                currentPosition = makeStepLoop(input, currentPosition);
            }
        } while (EVALUATOR != EXIT_VALUE);
    }

    private HorseFigure setPositionLoop(String input) {
        HorseFigure figure = new HorseFigure();
        if (input.isBlank()) {
            System.out.println("Blank input.");
            System.out.print("Your input: ");
            input = userInput.nextLine();
            return setPositionLoop(input);
        } else {
            if (input.equals(EXIT_VALUE)) {
                EVALUATOR = EXIT_VALUE;
            } else {
                if (!Pattern.matches(REGEX_ALPHABETICAL + REGEX_NUMERICAL, input)) {
                    System.out.println("Non-standard input. " +
                            "Input must be in form 'letter coordinate'" +
                            "'numerical coordinate', like 'A1'.");
                    System.out.print("Your input: ");
                    input = userInput.nextLine();
                    return setPositionLoop(input);
                } else {
                    String xCoordinate = "";
                    Matcher xMatch = Pattern.compile(REGEX_ALPHABETICAL).matcher(input);
                    while (xMatch.find()) {
                        xCoordinate = xMatch.group().toUpperCase();
                    }
                    long yCoordinate = 0;
                    Matcher yMatch = Pattern.compile(REGEX_NUMERICAL).matcher(input);
                    while (yMatch.find()) {
                        yCoordinate = Integer.parseInt(yMatch.group());
                    }
                    figure.setHorseCoordinateX(xCoordinate);
                    figure.setCoordinateY(yCoordinate);
                }
            }
        }
        return figure;
    }

    private String horseCoordinatesToString(HorseFigure horseFigure) {
        return horseFigure.getHorseCoordinateX() + horseFigure.getCoordinateY();
    }

    private HorseFigure makeStepLoop(String moveInput, HorseFigure position) {
        if (position == null) {
            HorseFigure basePosition = setPositionLoop(moveInput);
            System.out.println("*possible positions*");
            for (HorseFigure horseFigure : positionsSet(basePosition)) {
                System.out.print(horseFigure);
            }
            System.out.println();
            System.out.print("Your move: ");
            String inputNext = userInput.nextLine();
            HorseFigure movePosition = setPositionLoop(inputNext);
            for (HorseFigure horseFigure : positionsSet(basePosition)) {
                if (horseCoordinatesToString(horseFigure).equals(horseCoordinatesToString(movePosition))) {
                    System.out.println("Current position: " + movePosition);
                    System.out.println("*possible positions*");
                    for (HorseFigure showHorseFigure : positionsSet(movePosition)) {
                        System.out.print(showHorseFigure);
                    }
                    System.out.println();
                    System.out.print("Your move: ");
                    return movePosition;
                }
            }
            if (inputNext.equals(EXIT_VALUE)) {
                return null;
            } else {
                System.out.println("Impossible move");
                System.out.print("Your input: ");
                return basePosition;
            }
        } else {
            HorseFigure basePosition = position;
            HorseFigure movePosition = setPositionLoop(moveInput);
            for (HorseFigure horseFigure : positionsSet(basePosition)) {
                if (horseCoordinatesToString(horseFigure).equals(horseCoordinatesToString(movePosition))) {
                    System.out.println("Current position: " + movePosition);
                    System.out.println("*possible positions*");
                    for (HorseFigure showHorseFigure : positionsSet(movePosition)) {
                        System.out.print(showHorseFigure);
                    }
                    System.out.println();
                    System.out.print("Your move: ");
                    return movePosition;
                }
            }
            System.out.println("Impossible move");
            System.out.print("Your input: ");
            return basePosition;
        }
    }

    private long positionModifier(int modifierNumber, char coordinateAxis) {
        long[] position1 = {1, 2};
        long[] position2 = {2, 1};
        long[] position3 = {2, -1};
        long[] position4 = {1, -2};
        long[] position5 = {-1, -2};
        long[] position6 = {-2, -1};
        long[] position7 = {-2, 1};
        long[] position8 = {-1, 2};
        long[] workArray = new long[2];
        switch (modifierNumber) {
            case 1:
                workArray = position1;
                break;
            case 2:
                workArray = position2;
                break;
            case 3:
                workArray = position3;
                break;
            case 4:
                workArray = position4;
                break;
            case 5:
                workArray = position5;
                break;
            case 6:
                workArray = position6;
                break;
            case 7:
                workArray = position7;
                break;
            case 8:
                workArray = position8;
                break;
        }
        long returnValue = 0;
        switch (coordinateAxis) {
            case 'X':
                returnValue = workArray[0];
                break;
            case 'Y':
                returnValue = workArray[1];
        }
        return returnValue;
    }

    private HorseFigure[] positionsSet(HorseFigure horseFigure) {
        HorseFigure[] setOfAlternatives = new HorseFigure[0];
        for (int index = 0; index < 8; index++) {
            HorseFigure alternativePositions = new HorseFigure();
            alternativePositions.setHorseCoordinateX(CoordinateEncoder.alphabeticalCoordinateGeneration(horseFigure.getCoordinateX() + positionModifier(index + 1, 'X')));
            alternativePositions.setCoordinateY(horseFigure.getCoordinateY() + positionModifier(index + 1, 'Y'));
            if (alternativePositions.getCoordinateX() > 0 && alternativePositions.getCoordinateY() > 0) {
                setOfAlternatives = Arrays.copyOf(setOfAlternatives, setOfAlternatives.length + 1);
                setOfAlternatives[setOfAlternatives.length - 1] = alternativePositions;
            }
        }
        return setOfAlternatives;
    }
}
