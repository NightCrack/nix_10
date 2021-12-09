package ua.com.alevel.util;

import java.util.ArrayList;
import java.util.regex.Pattern;

public final class DateFormat {

    private static final String daysRegEx = "((0[1-9])|([1-2][0-9])|(3[0,1]))";
    private static final String monthsRegEx = "((0[1-9])|(1[0-2]))";
    private static final String yearsRegEx = "([1-9]([0-9]{0,3})?)";
    private static final String[] delimiters = {"/", "-"};
    private static final int[] INTERCALARY_MONTHS = {31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    private static final int[] ORDINARY_MONTHS = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    private DateFormat() {
    }

    private static String regExBuilder(int formatOpt) {
        StringBuilder string = new StringBuilder();
        String returnValue;
        switch (formatOpt) {
            case 1 -> {
                string.append(yearsRegEx).append(delimiters[0]).append(monthsRegEx).append(delimiters[0]).append(daysRegEx);
                returnValue = string.toString();
                return returnValue;
            }
            case 2 -> {
                string.append(daysRegEx).append(delimiters[0]).append(monthsRegEx).append(delimiters[0]).append(yearsRegEx);
                returnValue = string.toString();
                return returnValue;
            }
            case 3 -> {
                string.append(monthsRegEx).append(delimiters[1]).append(daysRegEx).append(delimiters[1]).append(yearsRegEx);
                returnValue = string.toString();
                return returnValue;
            }
            default -> {
                return null;
            }
        }
    }

    private static int inputCheck(String input) {
        boolean check = Pattern.compile(regExBuilder(1)).matcher(input).matches();
        if (check) {
            return 1;
        } else {
            check = Pattern.compile(regExBuilder(2)).matcher(input).matches();
            if (check) {
                return 2;
            } else {
                check = Pattern.compile(regExBuilder(3)).matcher(input).matches();
                if (check) {
                    return 3;
                }
                return 0;
            }
        }
    }

    private static <E extends Number> boolean dateValidation(E[] yMd) {
        return dateValidation(yMd[0], yMd[1], yMd[2]);
    }

    private static <E extends Number> boolean dateValidation(E year, E month, E day) {
        boolean isIntercalary = (year.intValue() % 4 == 0);
        int[] months = ORDINARY_MONTHS;
        if (isIntercalary) {
            months = INTERCALARY_MONTHS;
        }
        return day.intValue() <= months[month.intValue() - 1];
    }

    public static ArrayList<String> convertDate(String input) {
        ArrayList<String> matchedDates = StringEdit
                .parseMatchedValue(input, StringEdit
                        .regExBuilder(regExBuilder(1), regExBuilder(2), regExBuilder(3)));
        ArrayList<String> convertedDates = new ArrayList<>();
        for (String date : matchedDates) {
            convertedDates.add(reformat(date));
        }
        return convertedDates;
    }

    private static String reformat(String input) {
        int dateFormat = inputCheck(input);
        switch (dateFormat) {
            case 1 -> {
                String[] parsedDate = input.split(delimiters[0]);
                Integer[] convertedDate = new Integer[parsedDate.length];
                for (int index = 0; index < parsedDate.length; index++) {
                    convertedDate[index] = Integer.parseInt(parsedDate[index]);
                }
                if (dateValidation(convertedDate)) {
                    return parsedDate[0] + parsedDate[1] + parsedDate[2];
                }
            }
            case 2 -> {
                String[] parsedDate = input.split(delimiters[0]);
                Integer[] convertedDate = new Integer[parsedDate.length];
                for (int index = 0; index < parsedDate.length; index++) {
                    convertedDate[index] = Integer.parseInt(parsedDate[index]);
                }
                if (dateValidation(convertedDate[2], convertedDate[1], convertedDate[0])) {
                    return parsedDate[2] + parsedDate[1] + parsedDate[0];
                }
            }
            case 3 -> {
                String[] parsedDate = input.split(delimiters[1]);
                Integer[] convertedDate = new Integer[parsedDate.length];
                for (int index = 0; index < parsedDate.length; index++) {
                    convertedDate[index] = Integer.parseInt(parsedDate[index]);
                }
                if (dateValidation(convertedDate[2], convertedDate[0], convertedDate[1])) {
                    return parsedDate[2] + parsedDate[0] + parsedDate[1];
                }
            }
        }
        return null;
    }
}
