package ua.com.alevel.level1.util;

import java.util.Arrays;

public final class CoordinateEncoder {

    private static final String REGEX = "[0-9]+";
    private static final char startLetter = 'A';
    private static final long numberSystemSize = 26;

    private CoordinateEncoder() {
    }

    public static String alphabeticalCoordinateGeneration(long input) {
        long quotient = 1;
        long remainder;
        char[] resultingIndex = new char[0];
        while (input != 0) {
            resultingIndex = Arrays.copyOf(resultingIndex, (resultingIndex.length + 1));
            int letterModifier;
            quotient = input / numberSystemSize;
            remainder = input % numberSystemSize;
            if (quotient == 0) {
                letterModifier = (int) remainder;
                input = quotient;
            } else {
                if (remainder == 0) {
                    letterModifier = (int) numberSystemSize;
                    input = quotient - 1;
                } else {
                    letterModifier = (int) remainder;
                    input = quotient;
                }
            }
            resultingIndex[resultingIndex.length - 1] = (char) (startLetter + letterModifier - 1);
        }
        char[] reversedIndex = new char[resultingIndex.length];
        for (int index = 0; index < resultingIndex.length; index++) {
            reversedIndex[index] = resultingIndex[resultingIndex.length - index - 1];
        }
        return String.valueOf(reversedIndex);
    }

    public static long alphabeticalCoordinateDecoding(String input) {
        String[] extractedCoordinateX = input.split(REGEX);
        if (!input.isBlank()) {
            if ((extractedCoordinateX.length == 1 && !extractedCoordinateX[0].isBlank()) || (extractedCoordinateX.length > 1)) {
                if (extractedCoordinateX[0].isBlank()) {
                    System.arraycopy(extractedCoordinateX, 1, extractedCoordinateX, 0, (extractedCoordinateX.length - 1));
                }
                char[] decomposedCoordinateX = extractedCoordinateX[0].toUpperCase().toCharArray();
                int[] transformedCharArrayOfCoordinateX = new int[decomposedCoordinateX.length];
                for (int index = 0; index < decomposedCoordinateX.length; index++) {
                    transformedCharArrayOfCoordinateX[transformedCharArrayOfCoordinateX.length - index - 1] = decomposedCoordinateX[index] - startLetter + 1;
                }
                long coordinateX = 0;
                for (int index = 0, multiplier = 1; index < transformedCharArrayOfCoordinateX.length; index++) {
                    for (int multiplierIndex = 0; multiplierIndex < index; multiplierIndex++) {
                        multiplier *= numberSystemSize;
                    }
                    coordinateX += (long) multiplier * transformedCharArrayOfCoordinateX[index];
                    multiplier = 1;
                }
                return coordinateX;
            }
            return 0;
        }
        return 0;
    }

}
