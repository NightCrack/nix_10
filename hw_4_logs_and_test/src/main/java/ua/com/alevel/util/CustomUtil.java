package ua.com.alevel.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;

public final class CustomUtil {

    public static final SimpleDateFormat year = new SimpleDateFormat("yyyy");
    private static final int SECOND = 1000;
    private static final int MINUTE = 60;
    private static final int HOUR = 60;
    private static final int DAY = 24;
    private static final int[] ordinaryMonths = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    private static final int[] intercalaryMonths = {31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    private static final SimpleDateFormat month = new SimpleDateFormat("MM");
    private static final SimpleDateFormat day = new SimpleDateFormat("dd");

    private CustomUtil() {
    }

    private static boolean isIntercalary(int year) {
        int currentYear = year % 4;
        if (currentYear == 0) {
            return true;
        } else {
            return false;
        }
    }

    private static int yearSum(int[] months) {
        int returnSum = 0;
        for (int month : months) {
            returnSum += month;
        }
        return returnSum;
    }

    public static String dateToString(Date date) {
        if (date != null) {
            String returnDate = new SimpleDateFormat("MM.dd.yyyy").format(date);
            return returnDate;
        }
        return "";
    }

    public static String formatName(String name) {
        return name.substring(0, 1).toUpperCase(Locale.ROOT) +
                name.substring(1).toLowerCase(Locale.ROOT);
    }

    public static Date setDate(String date) {
        if (date != null) {
            String[] parsedDate = date.split("\\.");
            int[] returnArray = new int[parsedDate.length];
            for (int index = 0; index < parsedDate.length; index++) {
                returnArray[index] = Integer.parseInt(parsedDate[index]);
            }
            return setDate(returnArray[2], returnArray[0], returnArray[1]);
        }
        return null;
    }

    public static Date setDate(int year, int month, int day) {
        int monthsLengthInDays;
        int[] months;
        if (isIntercalary(year)) {
            months = new int[month - 1];
            System.arraycopy(ordinaryMonths, 0, months, 0, months.length);
        } else {
            months = new int[month];
            System.arraycopy(intercalaryMonths, 0, months, 0, months.length);
        }
        monthsLengthInDays = yearSum(months);
        int intercalaryYearsAmount = year / 4 - 1970 / 4;
        int ordinaryYearsAmount = year - 1970 - intercalaryYearsAmount;
        long yearValue = ((long) intercalaryYearsAmount * yearSum(intercalaryMonths) +
                (long) ordinaryYearsAmount * yearSum(ordinaryMonths) +
                monthsLengthInDays + (day - 1)) * DAY * HOUR * MINUTE * SECOND;
        Date date = new Date();
        date.setTime(yearValue);
        return date;
    }

    public static void printStringArray(String[] inputArray) {
        for (int index = 0; index < inputArray.length; index += 2) {
            if ((index + 1) < inputArray.length) {
                System.out.println(valueEvaluator(inputArray[index]) + " / " + valueEvaluator(inputArray[index + 1]));
            } else {
                System.out.println(valueEvaluator(inputArray[index]));
            }
        }
    }

    public static String composeStringArray(String[] inputArray) {
        String returnString = "";
        for (int index = 0; index < inputArray.length; index += 2) {
            if ((index + 1) < inputArray.length) {
                returnString += (valueEvaluator(inputArray[index]) + " / " + valueEvaluator(inputArray[index + 1])) + System.lineSeparator();
            } else {
                returnString += inputArray[index];
            }
        }
        return returnString;
    }

    private static String valueEvaluator(String value) {
        if (value == null) {
            return "";
        } else {
            return value;
        }
    }

    public static int lifespan(Date dateOfBirth, Date dateOfDeath) {
        int lifespan = Integer.parseInt(year.format(dateOfDeath)) - Integer.parseInt(year.format(dateOfBirth)) - 1;
        if ((Integer.parseInt(month.format(dateOfDeath)) >= Integer.parseInt(month.format(dateOfBirth))) &&
                (Integer.parseInt(day.format(dateOfDeath)) >= Integer.parseInt(day.format(dateOfBirth)))) {
            lifespan += 1;
        }
        return lifespan;
    }

    public static int lifespan(Date dateOfBirth) {
        Date date = new Date();
        return lifespan(dateOfBirth, date);
    }

    public static String composeDate(int month, int day, int year) {
        return String.valueOf(month) + '.' + day + '.' + year;
    }

    public static Date generateDate() {
        Date startDate = new Date(0);
        Date endDate = new Date();
        int yearRange = Integer.parseInt(year.format(endDate)) - Integer.parseInt(year.format(startDate)) - 18;
        int generatedYear = (int) (Math.random() * yearRange + Integer.parseInt(year.format(startDate)));
        int generatedMonth = (int) (Math.random() * (ordinaryMonths.length - 1) + 1);
        int[] currentYear = ordinaryMonths;
        if (isIntercalary(generatedYear)) {
            currentYear = intercalaryMonths;
        }
        int generatedDay = (int) (Math.random() * (currentYear[generatedMonth - 1] - 1) + 1);
        return setDate(generatedYear, generatedMonth, generatedDay);
    }

    public static boolean isNumber(String string) {
        return Pattern.compile("[0-9]++").matcher(string).matches();
    }

    public static Date generateDate(Date dateOfBirth) {
        Date endDate = new Date();
        int yearRange = Integer.parseInt(year.format(endDate)) - Integer.parseInt(year.format(dateOfBirth)) - 18;
        int generatedYear = (int) (Math.random() * yearRange + Integer.parseInt(year.format(dateOfBirth)) + 18);
        int generatedMonth = (int) (Math.random() * (ordinaryMonths.length - 1) + 1);
        int[] currentYear = ordinaryMonths;
        if (isIntercalary(generatedYear)) {
            currentYear = intercalaryMonths;
        }
        int generatedDay = (int) (Math.random() * (currentYear[generatedMonth - 1] - 1) + 1);
        return setDate(generatedYear, generatedMonth, generatedDay);
    }

    public static boolean checkUniqueName(String[] existingNamesArray, String nameToCheck) {
        for (int index = 0; index < existingNamesArray.length; index++) {
            if (existingNamesArray[index].equals(nameToCheck)) {
                return false;
            }
        }
        return true;
    }

    public static boolean datePatternCheck(String input) {
        String REGEX = "^(1[0-2]|0[1-9])\\.(3[01]|[12][0-9]|0[1-9])\\.[0-9]{4}$";
        boolean stringValidation = Pattern.compile(REGEX).matcher(input).matches();
        if (stringValidation) {
            String[] splitDate = input.split("\\.");
            Date today = new Date();
            boolean evaluateYear = (Integer.parseInt(splitDate[2]) <= Integer.parseInt(year.format(today))) && (Integer.parseInt(splitDate[2]) >= 1970);
            boolean evaluateMonth = (Integer.parseInt(splitDate[0]) <= 12 && (Integer.parseInt(splitDate[0]) >= 1));
            if (evaluateYear && evaluateMonth) {
                if (isIntercalary(Integer.parseInt(splitDate[2]))) {
                    boolean evaluateDay = (Integer.parseInt(splitDate[1]) <=
                            intercalaryMonths[Integer.parseInt(splitDate[0]) - 1]) &&
                            (Integer.parseInt(splitDate[1]) >= 1);
                    return evaluateDay;
                } else {
                    boolean evaluateDay = (Integer.parseInt(splitDate[1]) <=
                            intercalaryMonths[Integer.parseInt(splitDate[0]) - 1]) &&
                            (Integer.parseInt(splitDate[1]) >= 1);
                    return evaluateDay;
                }
            }
            return false;
        }
        return false;
    }

    public static boolean simpleDatePatternCheck(String input) {
        String REGEX = "^(1[0-2]|0[1-9])\\.(3[01]|[12][0-9]|0[1-9])\\.[0-9]{4}$";
        boolean stringValidation = Pattern.compile(REGEX).matcher(input).matches();
        if (stringValidation) {
            String[] splitDate = input.split("\\.");
            Date today = new Date();
            boolean evaluateYear = (Integer.parseInt(splitDate[2]) >= 1970);
            boolean evaluateMonth = (Integer.parseInt(splitDate[0]) <= 12 && (Integer.parseInt(splitDate[0]) >= 1));
            if (evaluateYear && evaluateMonth) {
                if (isIntercalary(Integer.parseInt(splitDate[2]))) {
                    boolean evaluateDay = (Integer.parseInt(splitDate[1]) <=
                            intercalaryMonths[Integer.parseInt(splitDate[0]) - 1]) &&
                            (Integer.parseInt(splitDate[1]) >= 1);
                    return evaluateDay;
                } else {
                    boolean evaluateDay = (Integer.parseInt(splitDate[1]) <=
                            intercalaryMonths[Integer.parseInt(splitDate[0]) - 1]) &&
                            (Integer.parseInt(splitDate[1]) >= 1);
                    return evaluateDay;
                }
            }
            return false;
        }
        return false;
    }

    public static String nameOpt(String[] namesArray) {
        return namesArray[(int) (Math.round(Math.random() * (namesArray.length - 1)))];
    }

    public static String createUniqueName(String[] existingNamesPool, String[] newNamesPool) {
        String name = nameOpt(newNamesPool);
        for (int index = 0; index < existingNamesPool.length; index++) {
            if (existingNamesPool[index].equals(name)) {
                return createUniqueName(existingNamesPool, newNamesPool);
            }
        }
        return name;
    }

}
