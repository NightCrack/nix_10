package ua.com.alevel.util;

import ua.com.alevel.customexception.WrongInputException;
import ua.com.alevel.entity.Date;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public final class Output {

    public static final String FULL_EXPRESSION = "";
    public static final String DATE_PART = "Date";
    public static final String TIME_PART = "Time";
    private static Output instance;
    private final String[] daysRegEx = {"([0-9]{2})", "([0-9]{1,2})"};
    private final String[] monthsRegEx = {"([0-9]{2})", "([0-9]{1,2})", "([a-zA-Z\\u0400-\\u04ff]{3,10})"};
    private final String[] yearsRegEx = {"([0-9]{1,2})", "([0-9]{1,4})"};
    private final String[] hoursRegEx = {"([0-9]{0,2})"};
    private final String[] minutesRegEx = {"([0-9]{0,2})"};
    private final String[] secondsRegEx = {"([0-9]{0,2})"};
    private final String[] millisecondsRegEx = {"([0-9]{0,3})"};
    private final String[] delimiters = {":", "/", " "};
    private final String[] separator = {" "};
    private final String[] monthsNames = {
            "January",
            "February",
            "March",
            "April",
            "May",
            "June",
            "July",
            "August",
            "September",
            "October",
            "November",
            "December"
    };
    private int inputFormatOpt = 1;
    private int outputFormatOpt = 1;

    private Output() {
    }

    public static Output getInstance() {
        if (instance == null) {
            instance = new Output();
        }
        return instance;
    }

    public int getOutputFormatOpt() {
        return outputFormatOpt;
    }

    public void setOutputFormatOpt(String outputFormatOpt) throws WrongInputException {
        try {
            int opt = Integer.parseInt(outputFormatOpt);
            if (opt > 0 && opt < 5) {
                this.outputFormatOpt = opt;
            }
        } catch (Exception exception) {
            throw new WrongInputException();
        }
    }

    public int getInputFormatOpt() {
        return inputFormatOpt;
    }

    public void setInputFormatOpt(String inputFormatOpt) throws WrongInputException {
        try {
            int opt = Integer.parseInt(inputFormatOpt);
            if (opt > 0 && opt < 5) {
                this.inputFormatOpt = opt;
            } else {
                throw new WrongInputException();
            }
        } catch (Exception exception) {
            throw new WrongInputException();
        }
    }

    private Integer inputMonthRecognition(String input) throws WrongInputException {
        String[] january = {"Jan([a-zA-Z]{4})?", "Січ([\\u0400-\\u04ff]{2,4})?", "Янв([\\u0400-\\u04ff]{3,4})?"};
        String[] february = {"Feb([a-zA-Z]{4})?", "Лют([\\u0400-\\u04ff]{2,3})?", "Фев([\\u0400-\\u04ff]{4,5})?"};
        String[] march = {"Mar([a-zA-Z]{2})?", "Бер([\\u0400-\\u04ff]{4,6})?", "Мар([\\u0400-\\u04ff]{1,3})?"};
        String[] april = {"Apr([a-zA-Z]{2})?", "Кві([\\u0400-\\u04ff]{3,5})?", "Апр([\\u0400-\\u04ff]{3,4})?"};
        String[] may = {"May", "Тра([\\u0400-\\u04ff]{3,5})?", "Ма[\\u0400-\\u0419\\u0421-\\u0439\\u0441-\\u04ff]{1}([\\u0400-\\u04ff]{1})?"};
        String[] june = {"Jun([a-zA-Z]{1})?", "Чер([\\u0400-\\u04ff]{3,5})?", "Июн([\\u0400-\\u04ff]{1,2})?"};
        String[] july = {"Jul([a-zA-Z]{1})?", "Лип([\\u0400-\\u04ff]{2,4})?", "Июл([\\u0400-\\u04ff]{1,2})?"};
        String[] august = {"Aug([a-zA-Z]{3})?", "Сер([\\u0400-\\u04ff]{3,5})?", "Авг([\\u0400-\\u04ff]{3,5})?"};
        String[] september = {"Sep([a-zA-Z]{7})?", "Вер([\\u0400-\\u04ff]{4,6})?", "Сен([\\u0400-\\u04ff]{5,6})?"};
        String[] october = {"Oct([a-zA-Z]{4})?", "Жов([\\u0400-\\u04ff]{3,5})?", "Окт([\\u0400-\\u04ff]{4,5})?"};
        String[] november = {"Nov([a-zA-Z]{5})?", "Лис([\\u0400-\\u04ff]{5,8})?", "Ноя([\\u0400-\\u04ff]{3,4})?"};
        String[] december = {"Dec([a-zA-Z]{5})?", "Гру([\\u0400-\\u04ff]{3,5})?", "Дек([\\u0400-\\u04ff]{4,5})?"};
        String[][] months = {january, february, march, april, may, june, july, august, september, october, november, december};
        for (int indexA = 0; indexA < months.length; indexA++) {
            for (int indexB = 0; indexB < months[indexA].length; indexB++) {
                if (Pattern.compile(months[indexA][indexB], Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE).matcher(input).matches()) {
                    return indexA + 1;
                }
            }
        }
        return null;
    }

    public String regExBuilder(int formatOpt, String expressionPart) {
        List<String> regEx = formatOpt(formatOpt);
        switch (expressionPart) {
            case TIME_PART -> {
                String returnValue = regEx.get(3) + "?" +
                        regEx.get(8) + "?" +
                        regEx.get(4) + "?" +
                        regEx.get(8) + "?" +
                        regEx.get(5) + "?" +
                        regEx.get(8) + "?" +
                        regEx.get(6) + "?";
                return returnValue;
            }
            case DATE_PART -> {
                String returnValue = regEx.get(0) + "?" +
                        regEx.get(7) + "?" +
                        regEx.get(1) + "?" +
                        regEx.get(7) + "?" +
                        regEx.get(2) + "?";
                return returnValue;
            }
            default -> {
                String returnValue = regEx.get(0) + "?" +
                        regEx.get(7) + "?" +
                        regEx.get(1) + "?" +
                        regEx.get(7) + "?" +
                        regEx.get(2) + "?" +
                        " " +
                        regEx.get(3) + "?" +
                        regEx.get(8) + "?" +
                        regEx.get(4) + "?" +
                        regEx.get(8) + "?" +
                        regEx.get(5) + "?" +
                        regEx.get(8) + "?" +
                        regEx.get(6) + "?";
                return returnValue;
            }
        }
    }

    private void inputCheck(String input) throws WrongInputException {
        boolean check = Pattern.compile(regExBuilder(this.inputFormatOpt, FULL_EXPRESSION)).matcher(input).matches();
        if (!check) {
            check = Pattern.compile(regExBuilder(this.inputFormatOpt, DATE_PART)).matcher(input).matches();
            if (!check) {
                check = Pattern.compile(regExBuilder(this.inputFormatOpt, TIME_PART)).matcher(input).matches();
                if (!check) {
                    throw new WrongInputException();
                }
            }
        }
    }

    private String hourRegExFormat(int formatOpt) {
        switch (formatOpt) {
            default -> {
                return hoursRegEx[0];
            }
        }
    }

    private String minuteRegExFormat(int formatOpt) {
        switch (formatOpt) {
            default -> {
                return minutesRegEx[0];
            }
        }
    }

    private String secondRegExFormat(int formatOpt) {
        switch (formatOpt) {
            default -> {
                return secondsRegEx[0];
            }
        }
    }

    private String millisecondRegExFormat(int formatOpt) {
        switch (formatOpt) {
            default -> {
                return millisecondsRegEx[0];
            }
        }
    }

    private String dayRegExFormat(int formatOpt) {
        switch (formatOpt) {
            case 2, 3 -> {
                return daysRegEx[1];
            }
            default -> {
                return daysRegEx[0];
            }
        }
    }

    private String monthRegExFormat(int formatOpt) {
        switch (formatOpt) {
            case 2 -> {
                return monthsRegEx[1];
            }
            case 3 -> {
                return monthsRegEx[2];
            }
            default -> {
                return monthsRegEx[0];
            }
        }
    }

    private String yearRegExFormat(int formatOpt) {
        switch (formatOpt) {
            case 2 -> {
                return yearsRegEx[1];
            }
            default -> {
                return yearsRegEx[0];
            }
        }
    }

    private String delimiterFormat(int formatOpt) {
        switch (formatOpt) {
            case 2 -> {
                return delimiters[1];
            }
            case 3 -> {
                return delimiters[2];
            }
            default -> {
                return delimiters[0];
            }
        }
    }

    private int[] formatOptPreset(int formatOpt) {
        switch (formatOpt) {
            case 2 -> {
                return new int[]{2, 2, 2, 1, 1, 1, 1, 2, 1};
            }
            case 3 -> {
                return new int[]{2, 3, 1, 1, 1, 1, 1, 3, 1};
            }
            case 4 -> {
                return new int[]{1, 3, 2, 1, 1, 1, 1, 3, 1};
            }
            default -> {
                return new int[]{1, 1, 1, 1, 1, 1, 1, 2, 1};
            }
        }
    }

    private int[] formatValuePositionPreset(int formatOpt) {
        switch (formatOpt) {
            case 2, 3 -> {
                return new int[]{2, 1, 3, 4, 5, 6, 7, 8, 9};
            }
            default -> {
                return new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9};
            }
        }
    }

    private List<String> formatOpt(int formatOpt) {
        List<String> returnFormat = new ArrayList<>();
        for (int index = 0; index < formatValuePositionPreset(formatOpt).length; index++) {
            switch (formatValuePositionPreset(formatOpt)[index]) {
                case 2 -> returnFormat.add(monthRegExFormat(formatOptPreset(formatOpt)[1]));
                case 3 -> returnFormat.add(yearRegExFormat(formatOptPreset(formatOpt)[2]));
                case 4 -> returnFormat.add(hourRegExFormat(formatOptPreset(formatOpt)[3]));
                case 5 -> returnFormat.add(minuteRegExFormat(formatOptPreset(formatOpt)[4]));
                case 6 -> returnFormat.add(secondRegExFormat(formatOptPreset(formatOpt)[5]));
                case 7 -> returnFormat.add(millisecondRegExFormat(formatOptPreset(formatOpt)[6]));
                case 8 -> returnFormat.add(delimiterFormat(formatOptPreset(formatOpt)[7]));
                case 9 -> returnFormat.add(delimiterFormat(formatOptPreset(formatOpt)[8]));
                default -> returnFormat.add(dayRegExFormat(formatOptPreset(formatOpt)[0]));
            }
        }
        return returnFormat;
    }

    private String[] parseTime(String input, int middleSpaceIndex) {
        String[] returnValueString = new String[]{"", "", "", ""};
        if (middleSpaceIndex < input.length() - 1) {
            String timePart = input.substring(middleSpaceIndex + 1);
            String[] parsedTimePart = timePart.split(formatOpt(this.inputFormatOpt).get(8));
            for (int index = 0; index < parsedTimePart.length; index++) {
                returnValueString[formatValuePositionPreset(this.inputFormatOpt)[index + 3] - 4] = parsedTimePart[index];
            }
        }
        return returnValueString;
    }

    private String[] parseDate(String input, int middleSpaceIndex) {
        String[] returnValueString = new String[]{"", "", ""};
        if (middleSpaceIndex > 0) {
            String datePart = input.substring(0, middleSpaceIndex);
            String[] parsedDatePart = datePart.split(formatOpt(this.inputFormatOpt).get(7));
            for (int index = 0; index < parsedDatePart.length; index++) {
                returnValueString[formatValuePositionPreset(this.inputFormatOpt)[index] - 1] = parsedDatePart[index];
            }
        }
        return returnValueString;
    }

    private int[] convertDateValues(String[] returnValueString) throws WrongInputException {
        int[] returnValue = {0, 0, 0, 0, 0, 0, 0};
        for (int indexA = 0; indexA < returnValue.length; indexA++) {
            if (returnValueString[indexA].equals("")) {
                returnValue[indexA] = 0;
            } else if (Pattern.compile("[0-9]+").matcher(returnValueString[indexA]).matches()) {
                returnValue[indexA] = Integer.parseInt(returnValueString[indexA]);
            } else {
                try {
                    returnValue[indexA] = inputMonthRecognition(returnValueString[indexA]);
                } catch (NullPointerException error) {
                    throw new WrongInputException();
                }
            }
        }
        for (int index = 0; index < 3; index++) {
            if (returnValue[index] != 0) {
                returnValue[index] = returnValue[index] - 1;
            }
        }
        if (this.inputFormatOpt != 2 && this.inputFormatOpt != 4) {
            returnValue[2] += 2000;
        }
        return returnValue;
    }

    private int[] parseInput(String input) throws WrongInputException {
        int[] returnValue = {0, 0, 0, 0, 0, 0, 0};
        boolean fullRegEx = Pattern.compile(regExBuilder(this.inputFormatOpt, FULL_EXPRESSION)).matcher(input).matches();
        boolean dateRegEx = Pattern.compile(regExBuilder(this.inputFormatOpt, DATE_PART)).matcher(input).matches();
        boolean timeRegEx = Pattern.compile(regExBuilder(this.inputFormatOpt, TIME_PART)).matcher(input).matches();
        String[] returnValueString = {"", "", "", "", "", "", ""};
        if (fullRegEx) {
            int middleSpaceIndex = input.lastIndexOf(separator[0]);
            String[] parsedTimePart = parseTime(input, middleSpaceIndex);
            System.arraycopy(parsedTimePart, 0, returnValueString, 3, parsedTimePart.length);
            String[] parsedDatePart = parseDate(input, middleSpaceIndex);
            System.arraycopy(parsedDatePart, 0, returnValueString, 0, parsedDatePart.length);
            System.arraycopy(convertDateValues(returnValueString), 0, returnValue, 0, returnValue.length);
            return returnValue;
        } else if (dateRegEx) {
            String[] parsedDatePart = parseDate(input, input.length());
            System.arraycopy(parsedDatePart, 0, returnValueString, 0, parsedDatePart.length);
            System.arraycopy(convertDateValues(returnValueString), 0, returnValue, 0, returnValue.length);
            return returnValue;
        } else if (timeRegEx) {
            String[] parsedTimePart = parseTime(input, -1);
            System.arraycopy(parsedTimePart, 0, returnValueString, 3, parsedTimePart.length);
            System.arraycopy(convertDateValues(returnValueString), 0, returnValue, 0, returnValue.length);
            return returnValue;
        }
        return null;
    }

    public Date setDate(String input, String timezone) throws WrongInputException {
        inputCheck(input);
        int[] parsedDate = parseInput(input);
        Date date = new Date(timezone, 0);
        date.setYear(parsedDate[2]);
        date.setMonth(parsedDate[1]);
        date.setDay(parsedDate[0]);
        date.setHour(parsedDate[3]);
        date.setMin(parsedDate[4]);
        date.setSec(parsedDate[5]);
        date.setMil(parsedDate[6]);
        return date;
    }

    private String dayOutputFormat(int day) {
        String returnValue;
        switch (outputFormatOpt) {
            case 2, 3 -> returnValue = String.valueOf(day);
            default -> {
                returnValue = ("0" + day);
                returnValue = returnValue.substring(returnValue.length() - 2);
            }
        }
        return returnValue;
    }

    private String monthOutputFormat(int month) {
        String returnValue;
        switch (outputFormatOpt) {
            case 2 -> returnValue = String.valueOf(month);
            case 3, 4 -> returnValue = monthsNames[month - 1];
            default -> {
                returnValue = ("0" + month);
                returnValue = returnValue.substring(returnValue.length() - 2);
            }
        }
        return returnValue;
    }

    private String yearOutputFormat(int year) {
        String returnValue;
        switch (outputFormatOpt) {
            case 2, 4 -> returnValue = String.valueOf(year);
            default -> {
                returnValue = ("0" + year);
                returnValue = returnValue.substring(returnValue.length() - 2);
            }
        }
        return returnValue;
    }

    private String hourOutputFormat(int hour) {
        String returnValue;
        switch (outputFormatOpt) {
            default -> {
                returnValue = ("0" + hour);
                returnValue = returnValue.substring(returnValue.length() - 2);
            }
        }
        return returnValue;
    }

    private String minuteOutputFormat(int minute) {
        String returnValue;
        switch (outputFormatOpt) {
            default -> {
                returnValue = ("0" + minute);
                returnValue = returnValue.substring(returnValue.length() - 2);
            }
        }
        return returnValue;
    }

    private String secondOutputFormat(int second) {
        String returnValue;
        switch (outputFormatOpt) {
            default -> {
                returnValue = ("0" + second);
                returnValue = returnValue.substring(returnValue.length() - 2);
            }
        }
        return returnValue;
    }

    private String millisecondOutputFormat(int millisecond) {
        String returnValue;
        switch (outputFormatOpt) {
            default -> {
                returnValue = ("00" + millisecond);
                returnValue = returnValue.substring(returnValue.length() - 3);
            }
        }
        return returnValue;
    }

    public String dateOutput(Date date) {
        StringBuilder returnValue = new StringBuilder();
        for (int index = 0; index < (formatValuePositionPreset(this.outputFormatOpt).length - 2); index++) {
            switch (formatValuePositionPreset(this.outputFormatOpt)[index]) {
                case 2 -> returnValue.append(monthOutputFormat(date.getMonth() + 1));
                case 3 -> returnValue.append(yearOutputFormat(date.getYear() + 1));
                case 4 -> returnValue.append(hourOutputFormat(date.getHour()));
                case 5 -> returnValue.append(minuteOutputFormat(date.getMin()));
                case 6 -> returnValue.append(secondOutputFormat(date.getSec()));
                case 7 -> returnValue.append(millisecondOutputFormat(date.getMil()));
                default -> returnValue.append(dayOutputFormat(date.getDay() + 1));
            }
            if (index < 2) {
                returnValue.append(delimiterFormat(formatOptPreset(this.outputFormatOpt)[7]));
            } else if (index == 2) {
                returnValue.append(separator[0]);
            } else if (index < (formatValuePositionPreset(this.outputFormatOpt).length - 3)) {
                returnValue.append(delimiterFormat(formatOptPreset(this.outputFormatOpt)[8]));
            }
        }
        return returnValue.toString();
    }
}
