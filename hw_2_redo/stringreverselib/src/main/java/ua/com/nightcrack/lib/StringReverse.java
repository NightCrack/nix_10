package ua.com.nightcrack.lib;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringReverse {
    public static String reverse(String... args) {
        String input;
        String regex;
        String[] criteria = {"^.+", ".+$"};
        // Separate input string
        input = args[0];
        // Separate criteria
        for (int index = 1; index < args.length; index++) {
            if (!args[index].isEmpty()) {
                criteria[index - 1] = args[index];
            }
        }
        // Recognize criteria
        if (Pattern.matches("^#\\d++", criteria[0])) {
            criteria[0] = "(?<=^.{" + criteria[0].replace("#", "") + "}).*";
        }
        if (Pattern.matches("^#\\d++", criteria[1])) {
            Integer index = input.length() - Integer.valueOf(criteria[1].replace("#", "")) - 1;
            criteria[1] = ".*(?=.{" + criteria[1].replaceAll(".++", index.toString()) + "}$)";
        }
        // Form regex based on the criteria
        if (args.length == 2 && !Pattern.matches("^#\\d++", args[1])) {
            regex = criteria[0];
        } else {
            regex = criteria[0] + criteria[1];
        }
        // Split the input by the criteria
        List<String> splitInput = new ArrayList<>();
        String regexBeginning = "^(.*?(?=" + regex + "))";
        String regexEnding = ".*" + regex;
        Matcher matchBeginning = Pattern.compile(regexBeginning).matcher(input);
        while (matchBeginning.find()) {
            splitInput.add(matchBeginning.group());
        }
        String ending = Pattern.compile(regexEnding).matcher(input).replaceAll("");
        String regexInBetween = "((?<=" + regex + ").*?(?=" + regex + "))";
        Matcher matchInBetween = Pattern.compile(regexInBetween).matcher(input);
        Matcher matchCriteria = Pattern.compile(regex).matcher(input);
        List<String> matched = new ArrayList<>();
        while (matchInBetween.find()) splitInput.add(matchInBetween.group());
        while (matchCriteria.find()) matched.add(matchCriteria.group());
        // Reverse input
        for (int indexA = 0; indexA < matched.size(); indexA++) {
            char[] reverseArray = matched.get(indexA).toCharArray();
            char[] tempArray = new char[reverseArray.length];
            for (int index = 0; index < reverseArray.length; index++) {
                tempArray[index] = reverseArray[reverseArray.length - 1 - index];
            }
            matched.set(indexA, String.valueOf(tempArray));
        }
        List<String> resultList = new ArrayList<>();
        for (int index = 0; index < (splitInput.size() + matched.size()); index++) {
            if ((index % 2 == 0)) {
                resultList.add(splitInput.get(index / 2));
            } else {
                resultList.add(matched.get(index / 2));
            }
        }
        resultList.add(ending);
        // Form result string
        StringBuilder stringBuilder = new StringBuilder();
        for (String part :
                resultList) {
            stringBuilder.append(part);
        }
        // Return result string
        return stringBuilder.toString();
    }
}