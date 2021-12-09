package ua.com.alevel.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static ua.com.alevel.util.FileOps.read;

public class StringEdit {

    private StringEdit() {
    }

    public static String regExBuilder(String... args) {
        StringBuilder regExpression = new StringBuilder();
        for (int index = 0; index < args.length; index++) {
            regExpression.append("(").append(args[index]).append(")");
            if (index < (args.length - 1)) {
                regExpression.append("|");
            }
        }
        return regExpression.toString();
    }

    public static ArrayList<String> parseMatchedValue(String input, String regEx) {
        Matcher check = Pattern.compile(regEx).matcher(input);
        ArrayList<String> output = new ArrayList<>();
        while (check.find()) {
            output.add(check.group());
        }
        return output;
    }

    public static String uniqueName(String path, String... regExParams) {
        ArrayList<String> input = new ArrayList<>();
        try {
            input = read(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ArrayList<String> parsedNames;
        Map<String, Integer> nameOccurrence = new LinkedHashMap<>();
        for (String line : input) {
            parsedNames = StringEdit.parseMatchedValue(line, regExBuilder(regExParams));
            for (String name : parsedNames) {
                if (nameOccurrence.containsKey(name)) {
                    nameOccurrence.replace(name, nameOccurrence.get(name) + 1);
                } else {
                    nameOccurrence.put(name, 1);
                }
            }
        }
        for (Map.Entry<String, Integer> name :
                nameOccurrence.entrySet()) {
            if (name.getValue().equals(1)) {
                return name.getKey();
            }
        }
        return "not found";
    }
}
