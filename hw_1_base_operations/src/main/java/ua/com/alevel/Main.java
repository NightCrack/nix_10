package ua.com.alevel;

import java.util.Scanner;
import ua.com.alevel.separator.*;
import ua.com.alevel.lessonopt.*;

public class Main {
    public static void main(String[] args) {
        menu();
        int exit = 0;
        Numbers numbers = new Numbers();
        Characters characters = new Characters();
        Schedule schedule = new Schedule();
        do{
            Scanner inputOpt = new Scanner(System.in);
            char opt = inputOpt.next().charAt(0);
            switch(opt) {
                case '1': numbers.numbers();
                    menu();
                    break;
                case '2': characters.characters();
                    menu();
                    break;
                case '3': schedule.schedule();
                    menu();
                    break;
                case 'q':
                case 'Q': exit = 1;
                    break;
                default: System.out.print(  System.lineSeparator() +
                                            "Input number from 1 to 3 to select task; input \"Q\" (or \"q\") to exit." +
                                            System.lineSeparator() + "Your input: ");

            }
        } while (exit != 1);
    }

    private static void menu() {
        System.out.print("Choose the task (type a corresponding number):" +
                System.lineSeparator() + System.lineSeparator() + "1: Sum of numbers." +
                System.lineSeparator() + "2: Occurrence counter." +
                System.lineSeparator() + "3: Classes schedule." +
                System.lineSeparator() + System.lineSeparator() +
                "Input \"Q\" (or \"q\") to exit." +
                System.lineSeparator() + "Your input: " );
    }
}