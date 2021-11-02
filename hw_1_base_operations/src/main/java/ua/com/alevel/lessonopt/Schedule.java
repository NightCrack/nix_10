package ua.com.alevel.lessonopt;

import java.util.Scanner;

public class Schedule {

    public void schedule() {
        int exit = 0;
        do {
            menu();
            Scanner buffer = new Scanner(System.in);
            String userInput = buffer.nextLine();
            switch (userInput) {
                case "q", "Q" -> exit = 1;
                case "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" -> timeResult(Integer.parseInt(userInput));
            }
        } while (exit != 1);
    }

    private void menu() {
        System.out.print(System.lineSeparator() +
                "Input lesson's number (from 1 to 10) and press \"Enter\". " +
                "Input \"Q\" (or \"q\") to exit." +
                System.lineSeparator() + "Lesson № ");
    }

    private void timeResult(int lesson) {
        int initialTime = 9;
        int hour = 60;
        int lessonLength = 45;
        int smallBreak = 5;
        int bigBreak = 15;
        int minutesTotal = lessonLength * lesson + smallBreak * (lesson / 2) + bigBreak * ((lesson - 1) / 2);
        System.out.println(System.lineSeparator() + "Lesson ends at " +
                (initialTime + minutesTotal / hour) + " " + (minutesTotal % hour));
    }

}