package ua.com.alevel.util;

public final class DateGenerator {

    private DateGenerator() {
    }

    private static int randomNumber() {
        return (int) (9 * Math.random());
    }

    private static int opt() {
        return (int) (Math.round(Math.random()));
    }

    private static String generateNumber(int numberLength) {
        StringBuilder returnString = new StringBuilder();
        for (int index = 0; index < numberLength; index++) {
            returnString.append(randomNumber());
        }
        return returnString.toString();
    }

    public static String randomDateString() {
        String[] delimiters = {"/", "-"};
        int[] numberLength = {2, 4};
        StringBuilder returnString = new StringBuilder();
        for (int index = 0; index < 5; index++) {
            if ((index + 1) % 2 == 1) {
                returnString.append(generateNumber(numberLength[opt()]));
            } else {
                returnString.append(delimiters[opt()]);
            }
        }
        return returnString.toString();
    }
}
