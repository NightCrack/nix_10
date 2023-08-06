package ua.com.alevel.separator;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Characters {

    public void characters() {
        Scanner inputStream = new Scanner(System.in);
        String input;
        while (true) {
            System.out.print(System.lineSeparator() + "This subprogram will parse your input and " + "count number of each unique symbol occurrence." + System.lineSeparator() + "Print <back> to return to the main menu." + System.lineSeparator() + "Your input: ");
            input = inputStream.nextLine();
            if (input.equals("back")) {
                break;
            } else if (input.isEmpty()) {
                System.out.println("Empty input");
            } else {
                char[] inputArray = input.toCharArray();
                Map<Character, Integer> charOccurrences = new HashMap<>();
                for (char c : inputArray) {
                    if (charOccurrences.containsKey(c)) {
                        charOccurrences.put(c, charOccurrences.get(c) + 1);
                    } else {
                        charOccurrences.put(c, 1);
                    }
                }
                System.out.println("Results:");
                charOccurrences.keySet().stream().sorted().forEach(result -> System.out.println(result + " -> " + charOccurrences.get(result)));
            }
        }
    }
}
