package ua.com.alevel.controller;

import ua.com.alevel.customexception.LessThanZeroException;
import ua.com.alevel.customexception.WrongInputException;
import ua.com.alevel.entity.Date;
import ua.com.alevel.util.Interaction;
import ua.com.alevel.util.Output;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class MainController {

    private static final String HELP = "help";
    private static final String EXIT = "exit";
    private static final String PENDING_INPUT = "PENDING_INPUT";
    private static final String STEP_TWO = "STEP_TWO";
    private static final String SET_INPUT_FORMAT = "setIF";
    private static final String GET_INPUT_FORMAT = "getIF";
    private static final String SET_OUTPUT_FORMAT = "setOF";
    private static final String GET_OUTPUT_FORMAT = "getOF";
    private static final String SET_TIME_ZONE = "setTZ";
    private static final String GET_TIME_ZONE = "getTZ";
    private static final String ENTER_DATE = "entD";
    private static final String GET_DATE = "getD";
    private static final String COMPARE_DATES = "comD";
    private static final String ARRANGE_DATES = "arrD";
    private static final String EDIT_DATE = "ediD";
    private static final String INCREASE = "inc";
    private static final String DECREASE = "dec";
    private static final String ASCENDING = "asc";
    private static final String DESCENDING = "des";
    private static final String YEAR = "yrs";
    private static final String MONTH = "mon";
    private static final String DAY = "day";
    private static final String HOUR = "hrs";
    private static final String MINUTE = "min";
    private static final String SECOND = "sec";
    private static final String MILLISECOND = "mil";
    private static final String END_SYMBOL = "0";
    private static int timezone = 2;
    private static List<Date> dates = new ArrayList<>();
    private static Output output = Output.getInstance();
    private Scanner userInput = new Scanner(System.in);

    public void run() {
        menu(HELP);
        while (true) {
            menu(PENDING_INPUT);
            menu(userInput.nextLine());
        }
    }

    private boolean checkIfNumber(String input) {
        return Pattern.compile("[0-9]++").matcher(input).matches();
    }

    private boolean checkIfExists(String input) {
        if (checkIfNumber(input)) {
            return Integer.parseInt(input) - 1 < dates.size();
        }
        return false;
    }

    private Date defineDate(String input) throws WrongInputException {
        if (checkIfExists(input)) {
            return dates.get(Integer.parseInt(input) - 1);
        }
        return output.setDate(input, String.valueOf(timezone));
    }

    private boolean editOpt(String input) throws WrongInputException {
        switch (input) {
            case INCREASE -> {
                return false;
            }
            case DECREASE -> {
                return true;
            }
            default -> throw new WrongInputException();
        }
    }

    private boolean sortOpt(String input) throws WrongInputException {
        switch (input) {
            case ASCENDING -> {
                return true;
            }
            case DESCENDING -> {
                return false;
            }
            default -> throw new WrongInputException();
        }
    }

    private Date dateEdit(Date date, String unitName, int unitValue, boolean subtracting) throws WrongInputException, LessThanZeroException {
        switch (unitName) {
            case YEAR -> {
                return Interaction.modifyYear(date, unitValue, subtracting);
            }
            case MONTH -> {
                return Interaction.modifyMonth(date, unitValue, subtracting);
            }
            case DAY -> {
                return Interaction.modifyDay(date, unitValue, subtracting);
            }
            case HOUR -> {
                return Interaction.modifyHour(date, unitValue, subtracting);
            }
            case MINUTE -> {
                return Interaction.modifyMinute(date, unitValue, subtracting);
            }
            case SECOND -> {
                return Interaction.modifySecond(date, unitValue, subtracting);
            }
            case MILLISECOND -> {
                return Interaction.modifyMillisecond(date, unitValue, subtracting);
            }
            default -> {
                throw new WrongInputException();
            }
        }
    }

    private void menu(String opt) {
        switch (opt) {
            case HELP -> {
                System.out.println();
                System.out.println("To set input format, print <" + SET_INPUT_FORMAT + ">; to get input format, print <" + GET_INPUT_FORMAT + ">");
                System.out.println("To set output format, print <" + SET_OUTPUT_FORMAT + ">; to get output format, print <" + GET_OUTPUT_FORMAT + ">");
                System.out.println("To set timezone, print <" + SET_TIME_ZONE + ">; to get timezone, print <" + GET_TIME_ZONE + ">");
                System.out.println("To enter date, print <" + ENTER_DATE + ">; to get specific date, print <" + GET_DATE + ">");
                System.out.println("To compare dates, print <" + COMPARE_DATES + ">, and print dates (or their positions in the list) one by one");
                System.out.println("To modify date, print <" + EDIT_DATE + ">, print date (or it's position in the list) and choose from the following options: ");
                System.out.println("To increase, print <" + INCREASE + ">; to decrease, print <" + DECREASE + ">; next,");
                System.out.println("input the unit's value, and select unit: ");
                System.out.println("year <" + YEAR + ">, month <" + MONTH + ">, day <" + DAY + ">,");
                System.out.println("hour <" + HOUR + ">, minute <" + MINUTE + ">, second <" + SECOND + ">, millisecond <" + MILLISECOND + ">");
                System.out.println("To arrange dates, print <" + ARRANGE_DATES + ">; then opt arrange order: <" +
                        DESCENDING + "> as descending, or <" + ASCENDING + "> as ascending;");
                System.out.println("print dates (or their positions in the list) one by one, finishing your entire input with <" + END_SYMBOL + "> symbol");
                System.out.println("To get help, print <" + HELP + ">");
                System.out.println("To exit the program, print <" + EXIT + ">");
                System.out.println();
            }
            case PENDING_INPUT -> {
                System.out.println();
                System.out.print("<" + HELP + "> You want to: ");
            }
            case STEP_TWO -> System.out.print(" -> ");
            case EXIT -> {
                System.exit(0);
            }
            case ARRANGE_DATES -> {
                try {
                    menu(STEP_TWO);
                    boolean ascending = sortOpt(userInput.nextLine());
                    menu(STEP_TWO);
                    List<Date> dates = new ArrayList<>();
                    String input = "";
                    while (!input.equals(END_SYMBOL)) {
                        input = userInput.nextLine();
                        if (checkIfNumber(input) && Integer.parseInt(input) > 0) {
                            dates.add(defineDate(input));
                        }
                        menu(STEP_TWO);
                    }
                    dates = Interaction.sortDates(dates, ascending);
                    System.out.println(System.lineSeparator());
                    System.out.println("Ordered dates: ");
                    for (Date date : dates) {
                        System.out.println(output.dateOutput(date));
                    }
                } catch (WrongInputException exception) {
                    System.out.println(exception);
                }
            }
            case EDIT_DATE -> {
                try {
                    menu(STEP_TWO);
                    Date date = defineDate(userInput.nextLine());
                    menu(STEP_TWO);
                    boolean subtracting = editOpt(userInput.nextLine());
                    menu(STEP_TWO);
                    String input = userInput.nextLine();
                    if (!checkIfNumber(input)) {
                        throw new WrongInputException();
                    }
                    int unitValue = Integer.parseInt(input);
                    menu(STEP_TWO);
                    System.out.println("Result: " + output
                            .dateOutput(dateEdit(date, userInput.nextLine(), unitValue, subtracting)));
                } catch (WrongInputException | LessThanZeroException exception) {
                    System.out.println(exception);
                }
            }
            case COMPARE_DATES -> {
                try {
                    menu(STEP_TWO);
                    Date firstDate = defineDate(userInput.nextLine());
                    menu(STEP_TWO);
                    Date secondDate = defineDate(userInput.nextLine());
                    Date result = Interaction.getDifference(firstDate, secondDate);
                    boolean resultIsNull = true;
                    System.out.println(System.lineSeparator());
                    System.out.print("The difference: ");
                    if (result.getYear() > 0) {
                        resultIsNull = false;
                        System.out.print(result.getYear() + " years ");
                    }
                    if (result.getMonth() > 0) {
                        resultIsNull = false;
                        System.out.print(result.getMonth() + " months ");
                    }
                    if (result.getDay() > 0) {
                        resultIsNull = false;
                        System.out.print(result.getDay() + " days ");
                    }
                    if (result.getHour() > 0) {
                        resultIsNull = false;
                        System.out.print(result.getHour() + " hours ");
                    }
                    if (result.getMin() > 0) {
                        resultIsNull = false;
                        System.out.print(result.getMin() + " minutes ");
                    }
                    if (result.getSec() > 0) {
                        resultIsNull = false;
                        System.out.print(result.getSec() + " seconds ");
                    }
                    if (result.getMil() > 0) {
                        resultIsNull = false;
                        System.out.print(result.getMil() + " milliseconds");
                    }
                    if (resultIsNull) {
                        System.out.print("is absent");
                    }
                    System.out.println();
                } catch (WrongInputException exception) {
                    System.out.println(exception);
                }
            }
            case ENTER_DATE -> {
                try {
                    menu(STEP_TWO);
                    Date date = output.setDate(userInput.nextLine(), String.valueOf(timezone));
                    date.setTimeZone(String.valueOf(timezone));
                    dates.add(date);
                } catch (WrongInputException exception) {
                    System.out.println(exception);
                }
            }
            case GET_DATE -> {
                try {
                    menu(STEP_TWO);
                    int index;
                    try {
                        index = Integer.parseInt(userInput.nextLine());
                    } catch (Exception exception) {
                        throw new WrongInputException();
                    }
                    if (index > dates.size()) {
                        throw new WrongInputException();
                    }
                    System.out.println();
                    System.out.println(output.dateOutput(dates.get(index - 1)));
                } catch (WrongInputException exception) {
                    System.out.println(exception);
                }
            }
            case SET_INPUT_FORMAT -> {
                menu(STEP_TWO);
                try {
                    output.setInputFormatOpt(userInput.nextLine());
                } catch (WrongInputException exception) {
                    System.out.println(exception);
                }
            }
            case SET_OUTPUT_FORMAT -> {
                menu(STEP_TWO);
                try {
                    output.setOutputFormatOpt(userInput.nextLine());
                } catch (WrongInputException exception) {
                    System.out.println(exception);
                }
            }
            case SET_TIME_ZONE -> {
                menu(STEP_TWO);
                try {
                    String input = userInput.nextLine();
                    for (Date date : dates) {
                        date.setTimeZone(input);
                    }
                    timezone = Integer.parseInt(input);
                } catch (WrongInputException exception) {
                    System.out.println(exception);
                }
            }
            case GET_INPUT_FORMAT -> System.out.println(System.lineSeparator() + "Current input format (" +
                    output.getInputFormatOpt() + "): " +
                    output.regExBuilder(output.getInputFormatOpt(), Output.FULL_EXPRESSION));
            case GET_OUTPUT_FORMAT -> System.out.println(System.lineSeparator() + "Current out format (" +
                    output.getOutputFormatOpt() + "): " +
                    output.regExBuilder(output.getOutputFormatOpt(), Output.FULL_EXPRESSION));
            case GET_TIME_ZONE -> System.out.println(System.lineSeparator() + "Current timezone is: " + timezone);
        }
    }
}
