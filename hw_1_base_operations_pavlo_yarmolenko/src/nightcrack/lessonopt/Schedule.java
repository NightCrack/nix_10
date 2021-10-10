package nightcrack.lessonopt;

import java.util.*;

public class Schedule {
    public void schedule() {
        int exit = 0;
        do{
            menu();
            int initialTime = 9;
            int hour = 60;
            int lessonLength = 45;
            int smallBreak = 5;
            int bigBreak = 15;
            Scanner buffer = new Scanner(System.in);
            String userInput = buffer.nextLine();
            switch(userInput) {
                case "q":
                case "Q": exit = 1;
                    break;
                case "1": System.out.println( System.lineSeparator() + "Lesson ends at " +
                        (initialTime + (lessonLength*(Integer.valueOf(userInput)) + smallBreak*((Integer.valueOf(userInput))/2) + bigBreak*(((Integer.valueOf(userInput)) - 1)/2))/hour) +
                        " " + ((lessonLength*(Integer.valueOf(userInput)) + smallBreak*((Integer.valueOf(userInput))/2) + bigBreak*(((Integer.valueOf(userInput)) - 1)/2))%hour) );
                    break;
                case "2": System.out.println( System.lineSeparator() + "Lesson ends at " +
                        (initialTime + (lessonLength*(Integer.valueOf(userInput)) + smallBreak*((Integer.valueOf(userInput))/2) + bigBreak*(((Integer.valueOf(userInput)) - 1)/2))/hour) +
                        " " + ((lessonLength*(Integer.valueOf(userInput)) + smallBreak*((Integer.valueOf(userInput))/2) + bigBreak*(((Integer.valueOf(userInput)) - 1)/2))%hour) );
                    break;
                case "3": System.out.println( System.lineSeparator() + "Lesson ends at " +
                        (initialTime + (lessonLength*(Integer.valueOf(userInput)) + smallBreak*((Integer.valueOf(userInput))/2) + bigBreak*(((Integer.valueOf(userInput)) - 1)/2))/hour) +
                        " " + ((lessonLength*(Integer.valueOf(userInput)) + smallBreak*((Integer.valueOf(userInput))/2) + bigBreak*(((Integer.valueOf(userInput)) - 1)/2))%hour) );
                    break;
                case "4": System.out.println( System.lineSeparator() + "Lesson ends at " +
                        (initialTime + (lessonLength*(Integer.valueOf(userInput)) + smallBreak*((Integer.valueOf(userInput))/2) + bigBreak*(((Integer.valueOf(userInput)) - 1)/2))/hour) +
                        " " + ((lessonLength*(Integer.valueOf(userInput)) + smallBreak*((Integer.valueOf(userInput))/2) + bigBreak*(((Integer.valueOf(userInput)) - 1)/2))%hour) );
                    break;
                case "5": System.out.println( System.lineSeparator() + "Lesson ends at " +
                        (initialTime + (lessonLength*(Integer.valueOf(userInput)) + smallBreak*((Integer.valueOf(userInput))/2) + bigBreak*(((Integer.valueOf(userInput)) - 1)/2))/hour) +
                        " " + ((lessonLength*(Integer.valueOf(userInput)) + smallBreak*((Integer.valueOf(userInput))/2) + bigBreak*(((Integer.valueOf(userInput)) - 1)/2))%hour) );
                    break;
                case "6": System.out.println( System.lineSeparator() + "Lesson ends at " +
                        (initialTime + (lessonLength*(Integer.valueOf(userInput)) + smallBreak*((Integer.valueOf(userInput))/2) + bigBreak*(((Integer.valueOf(userInput)) - 1)/2))/hour) +
                        " " + ((lessonLength*(Integer.valueOf(userInput)) + smallBreak*((Integer.valueOf(userInput))/2) + bigBreak*(((Integer.valueOf(userInput)) - 1)/2))%hour) );
                    break;
                case "7": System.out.println( System.lineSeparator() + "Lesson ends at " +
                        (initialTime + (lessonLength*(Integer.valueOf(userInput)) + smallBreak*((Integer.valueOf(userInput))/2) + bigBreak*(((Integer.valueOf(userInput)) - 1)/2))/hour) +
                        " " + ((lessonLength*(Integer.valueOf(userInput)) + smallBreak*((Integer.valueOf(userInput))/2) + bigBreak*(((Integer.valueOf(userInput)) - 1)/2))%hour) );
                    break;
                case "8": System.out.println( System.lineSeparator() + "Lesson ends at " +
                        (initialTime + (lessonLength*(Integer.valueOf(userInput)) + smallBreak*((Integer.valueOf(userInput))/2) + bigBreak*(((Integer.valueOf(userInput)) - 1)/2))/hour) +
                        " " + ((lessonLength*(Integer.valueOf(userInput)) + smallBreak*((Integer.valueOf(userInput))/2) + bigBreak*(((Integer.valueOf(userInput)) - 1)/2))%hour) );
                    break;
                case "9": System.out.println( System.lineSeparator() + "Lesson ends at " +
                        (initialTime + (lessonLength*(Integer.valueOf(userInput)) + smallBreak*((Integer.valueOf(userInput))/2) + bigBreak*(((Integer.valueOf(userInput)) - 1)/2))/hour) +
                        " " + ((lessonLength*(Integer.valueOf(userInput)) + smallBreak*((Integer.valueOf(userInput))/2) + bigBreak*(((Integer.valueOf(userInput)) - 1)/2))%hour) );
                    break;
                case "10": System.out.println( System.lineSeparator() + "Lesson ends at " +
                        (initialTime + (lessonLength*(Integer.valueOf(userInput)) + smallBreak*((Integer.valueOf(userInput))/2) + bigBreak*(((Integer.valueOf(userInput)) - 1)/2))/hour) +
                        " " + ((lessonLength*(Integer.valueOf(userInput)) + smallBreak*((Integer.valueOf(userInput))/2) + bigBreak*(((Integer.valueOf(userInput)) - 1)/2))%hour) );
                    break;
            }

        } while ( exit != 1 );

    }
    private void menu() {
        System.out.print( System.lineSeparator() +
                            "Input lesson's number (from 1 to 10) and press \"Enter\"." +
                            "Input \"Q\" (or \"q\") to exit." +
                            System.lineSeparator() + "Lesson № ");
    }

}