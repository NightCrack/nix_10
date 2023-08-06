package ua.com.alevel;

import ua.com.alevel.lessonopt.Schedule;
import ua.com.alevel.separator.Characters;
import ua.com.alevel.separator.Numbers;

import java.util.Scanner;

import static java.lang.System.out;

public class Main {
    public static void main(String[] args) {
        Numbers numbers = new Numbers();
        Characters characters = new Characters();
        Schedule schedule = new Schedule();
        while (true) {
            menu();
            Scanner inputStream = new Scanner(System.in);
            String input = inputStream.nextLine();
            switch (input) {
                case "1" -> numbers.numbers();
                case "2" -> characters.characters();
                case "3" -> schedule.schedule();
                case "quit" -> System.exit(0);
                default -> out.println(System.lineSeparator() + "Wrong input.");
            }
        }
    }

    private static void menu() {
        out.println("Choose the task (type a corresponding number):");
        out.println(System.lineSeparator() + "1: Compare numbers.");
        out.println("2: Occurrence counter.");
        out.println("3: Classes schedule.");
        out.println(System.lineSeparator() + "Input \"quit\" to exit.");
        out.print("Your input: ");
    }
}