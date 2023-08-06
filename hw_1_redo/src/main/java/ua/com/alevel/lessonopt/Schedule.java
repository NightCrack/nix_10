package ua.com.alevel.lessonopt;

import java.util.Scanner;
import java.util.regex.Pattern;

public class Schedule {

    private final int SEC = 1;
    private final int MIN = SEC * 60;
    private final int HOUR = MIN * 60;
    private final int ACADEMIC_HOUR = MIN * 45;
    private final int BREAK = MIN * 10;
    private final int BIG_BREAK = MIN * 15;
    private final int CLASSES_START_HOURS = 8;
    private final int CLASSES_START_MIN = 0;

    public void schedule() {
        Scanner inputStream = new Scanner(System.in);
        while (true) {
            System.out.print(System.lineSeparator() +
                    "This subprogram will return a classes schedule." +
                    System.lineSeparator() + "Print <back> to return to the main menu." +
                    System.lineSeparator() + "Input a class number (from 1 to 7): ");
            String input = inputStream.nextLine();
            if (input.equals("back")) break;
            if (input.isBlank()) System.out.println("Empty input");
            if (!Pattern.matches("^[1-7]$", input)) {
                System.out.println("Wrong input. It must be a number from 1 to 7.");
            } else {
                System.out.println("The lesson " + input + " begins at " + timeCounter(input));
            }
        }
    }

    private String timeCounter(String lesson) {
        int intLesson = Integer.parseInt(lesson);
        int lessonTime = CLASSES_START_HOURS * HOUR +
                CLASSES_START_MIN * MIN +
                ACADEMIC_HOUR * intLesson +
                BREAK * (intLesson / 2) +
                BIG_BREAK * ((intLesson - 1) / 2);
        String hours = String.valueOf(lessonTime / HOUR);
        if (hours.length() < 2) hours = "0" + hours;
        String minutes = String.valueOf((lessonTime % HOUR) / MIN);
        if (minutes.length() < 2) minutes = "0" + hours;
        return hours + ":" + minutes;
    }
}
