package ua.com.alevel;

import java.util.Arrays;
import java.util.regex.*;

public final class ReverseString {

    private ReverseString () {};

    private static String symbolRegex = "[^\\w\\u0400-\\u04ff]+",
            wordRegex = "[\\w\\u0400-\\u04ff]+",
            wordOnlyRegex = "[\\w\\u0400-\\u04ff]++";

    private static boolean numberCheckup (String userInput) {
        char[] inputCharArray = userInput.toCharArray();
        int[] transferArray = new int[inputCharArray.length];
        for (int i = 0; i < inputCharArray.length; i++)
        {
            if (inputCharArray[i] <= '9' && inputCharArray[i] >= '0')
            {
                transferArray[i] = Character.getNumericValue(inputCharArray[i]) + 1;
            } else {

                transferArray[i] = 0;
            }
        }
        int arraySum = 0;
        for(int i = 0; i < transferArray.length; i++) {

            arraySum += transferArray[i];
        }
        if (arraySum == 0)
        {

            return false;
        } else {

            return true;
        }
    }

    private static int lastCriteriaIndex(String userInput, String startCriteria, String endCriteria) {

        int startIndex = 0;
        int wordStart = 0;
        int wordEnd = 0;
        int previousWordsEnd = 0;
        int endIndex = 0;
        for (;(wordStart != -1) && (wordEnd != -1);) {

            wordStart = userInput.indexOf(startCriteria, startIndex);
            if (wordStart != -1){

                previousWordsEnd = wordEnd;
                endIndex = wordStart + startCriteria.length();
                if (endIndex < userInput.length()) {

                    wordEnd = userInput.indexOf(endCriteria, endIndex);
                    if (wordEnd != -1) {

                        startIndex = wordEnd + endCriteria.length();
                    }
                }
            }
        }
        if (wordStart == -1) {

            return wordEnd + 1;
        } else {

            return previousWordsEnd + 1;
        }
    }

    public static String reverse(String userInput) {

        if (userInput == "") {

            return "Empty input";
        } else {

            String firstSymbol = String.valueOf(userInput.charAt(0));
            String[] words = userInput.split(symbolRegex);
            String[] symbols = userInput.split(wordRegex);
            for (int wordPosition = 0; wordPosition < words.length; wordPosition++) {

                char[] charArray = words[wordPosition].toCharArray();
                char[] reversedCharArray = new char[charArray.length];
                for (int character = 0; character < charArray.length; character++) {

                    reversedCharArray[character] = charArray[charArray.length - character - 1];
                }
                words[wordPosition] = String.valueOf(reversedCharArray);
            }
            int length = 0;
            if (((words.length == 0) && (symbols.length != 0))||((words.length != 0) && (symbols.length == 0))) {

                length = words.length + symbols.length;
            } else if ((words.length != 0) && (symbols.length != 0)) {

                length = words.length + symbols.length - 1;
            }
            String[] reversedWordsArray = new String[length];
            if (firstSymbol.matches(wordOnlyRegex)) {

                for (int word = 0, finalWord = 0; word < words.length && finalWord < reversedWordsArray.length; word++, finalWord += 2) {

                    reversedWordsArray[finalWord] = words[word];
                }
                for (int symbol = 1, finalWord = 1; symbol < symbols.length && finalWord < reversedWordsArray.length; symbol++, finalWord += 2) {

                    reversedWordsArray[finalWord] = symbols[symbol];
                }
            } else {

                for (int word = 1, finalWord = 1; word < words.length && finalWord < reversedWordsArray.length; word++, finalWord += 2) {

                    reversedWordsArray[finalWord] = words[word];
                }
                for (int symbol = 0, finalWord = 0; symbol < symbols.length && finalWord < reversedWordsArray.length; symbol++, finalWord += 2) {

                    reversedWordsArray[finalWord] = symbols[symbol];
                }
            }
            StringBuilder finalString = new StringBuilder();
            for (String word : reversedWordsArray) {

                finalString.append(word);
            }
            return finalString.toString();
        }
    }

    public static String reverse(String userInput, String criteria) {

        String partialRegexBeginning = "^(.*?(?=" + criteria + "))",
        partialRegexCriteria = criteria,
        partialRegexBetween = "((?<=" + criteria + ").*?(?=" + criteria + "))";
        boolean checkup = Pattern.matches(wordOnlyRegex, criteria);
        if (userInput == "") {

            return "Empty input";
        } else if (!checkup) {

            return System.lineSeparator() + "Wrong input \"criteria\" value. Input value without non-word symbols." + System.lineSeparator();
        } else {

            Matcher matchStart = Pattern.compile(partialRegexBeginning).matcher(userInput);
            String stringBeginning = null;
            int beginningLength = 0;
            while (matchStart.find()) {
                stringBeginning = matchStart.group();
            }
            Matcher matchCriteria = Pattern.compile(partialRegexCriteria).matcher(userInput);
            int index = 0;
            String[] stringCriteria = new String[index];
            while (matchCriteria.find()) {

                stringCriteria = Arrays.copyOf(stringCriteria, ++index);
                char[] charArray = matchCriteria.group().toCharArray();
                char[] reversedCharArray = new char[charArray.length];
                for (int character = 0; character < charArray.length; character++) {

                    reversedCharArray[character] = charArray[charArray.length - character - 1];
                }
                stringCriteria [index-1] = String.valueOf(reversedCharArray);
            }
            Matcher matchBetween = Pattern.compile(partialRegexBetween).matcher(userInput);
            index = 0;
            String[] stringBetween = new String[index];
            while (matchBetween.find()) {

                stringBetween = Arrays.copyOf(stringBetween, ++index);
                stringBetween [index-1] = matchBetween.group();
            }
            if (stringBeginning != null) {

                beginningLength = 1;
            }
            String[] partiallyReversedWordsArray = new String[beginningLength + stringCriteria.length + stringBetween.length];
            index = 0;
            if (stringBeginning != null) {

                partiallyReversedWordsArray[index] = stringBeginning;
                index++;
            }
            for (int criteriaIndex = 0; criteriaIndex < stringCriteria.length && index < partiallyReversedWordsArray.length; criteriaIndex++, index += 2) {

                partiallyReversedWordsArray[index] = stringCriteria[criteriaIndex];
            }
            if (stringBeginning != null) {

                index = 2;
            } else {

                index = 1;
            }
            for (int betweenIndex = 0; betweenIndex < stringBetween.length && index < (partiallyReversedWordsArray.length - 1); betweenIndex++, index += 2) {

                partiallyReversedWordsArray[index] = stringBetween[betweenIndex];
            }
            char[] userInputChar = userInput.toCharArray();
            StringBuilder preFinal = new StringBuilder();
            for (String string : partiallyReversedWordsArray) {

                preFinal.append(string);
            }
            String preFinalString = preFinal.toString();
            char[] preFinalChar = preFinalString.toCharArray();
            for(int charIndex = 0; charIndex < preFinalChar.length; charIndex++) {

                userInputChar[charIndex] = preFinalChar[charIndex];
            }
            StringBuilder finalString = new StringBuilder();
            for (char symbol : userInputChar) {

                finalString.append(symbol);
            }
            return finalString.toString();
        }
    }

    public static String reverse (String userInput, String startCriteria, String endCriteria, boolean criteriaOpt) {

        if (!userInput.isBlank()){

            if (criteriaOpt) {

                if (numberCheckup(startCriteria) && numberCheckup(endCriteria)) {

                    char[] charUserInput = userInput.toCharArray();
                    int startIndex = Integer.parseInt(startCriteria);
                    int endIndex = Integer.parseInt(endCriteria);
                    if (startIndex <= endIndex) {

                        if (endIndex <= charUserInput.length) {

                            char[] charArrayBeginning = new char[startIndex];
                            char[] charWorkArray = new char[endIndex - startIndex + 1];
                            char[] charArrayEnd = new char[charUserInput.length - endIndex - 1];
                            System.arraycopy(charUserInput, 0, charArrayBeginning, 0, charArrayBeginning.length);
                            System.arraycopy(charUserInput, startIndex, charWorkArray, 0, charWorkArray.length);
                            System.arraycopy(charUserInput, (endIndex + 1), charArrayEnd, 0, charArrayEnd.length);
                            String workArray = String.valueOf(charWorkArray);
                            String startString = String.valueOf(charArrayBeginning);
                            String middleString = reverse(workArray);
                            String endString = String.valueOf(charArrayEnd);
                            String[] endArray = {startString, middleString, endString};
                            if ((startString == null) && (endString != null)) {

                                System.arraycopy(endArray, 1, endArray, 0, 2);
                            } else if ((startString != null) && (endString == null)) {

                                System.arraycopy(endArray, 0, endArray, 0, 2);
                            } else if ((startString == null) && (endString == null)) {

                                System.arraycopy(endArray, 1, endArray, 0, 1);
                            }
                            StringBuilder finalString = new StringBuilder();
                            for (String string : endArray) {

                                finalString.append(string);
                            }
                            return finalString.toString();
                        } else {

                            return "The End Index is greater than overall length of the Input";
                        }
                    } else {

                        return "The End Index is lesser than the Beginning";
                    }
                } else {

                    return "Empty (not numerical) Beginning or End Index";
                }
            } else {

                if (userInput.contains(startCriteria) && userInput.contains(endCriteria)) {

                    if (userInput.indexOf(startCriteria) < userInput.indexOf(endCriteria)) {

                        int startIndex = 0;
                        int wordStart = 0;
                        int wordEnd = 0;
                        int endIndex = 0;
                        int[] wordIndexes = new int[2];
                        int startEndIndex = 0;
                        String[] wordsArray = new String[0];
                        String[] betweenArray = new String[0];
                        char[] charUserInput = userInput.toCharArray();
                        startIndex = userInput.indexOf(startCriteria);
                        char[] charArrayBeginning = new char[startIndex];
                        System.arraycopy(charUserInput, 0, charArrayBeginning, 0, startIndex);
                        String startString = String.valueOf(charArrayBeginning);
                        startIndex = lastCriteriaIndex(userInput, startCriteria, endCriteria);
                        char[] charArrayEnd = new char[(charUserInput.length - startIndex)];
                        System.arraycopy(charUserInput, startIndex, charArrayEnd, 0, (charUserInput.length - startIndex));
                        String endString = String.valueOf(charArrayEnd);
                        startIndex = 0;
                        for (int symbolIndex = 0, prevSymbolIndex = 0, wordIndex = 0, betweenIndex = 0; (wordStart != -1) && (wordEnd != -1); ) {

                            wordStart = userInput.indexOf(startCriteria, startIndex);
                            if (wordStart != -1) {

                                if (symbolIndex != prevSymbolIndex) {

                                    ++betweenIndex;
                                    betweenArray = Arrays.copyOf(betweenArray, betweenIndex);
                                    int tempBetweenArrayLength = wordStart - wordIndexes[0] - wordIndexes[1];
                                    char[] tempBetweenArray = new char[tempBetweenArrayLength];
                                    System.arraycopy(charUserInput, (wordEnd + endCriteria.length()), tempBetweenArray, 0, tempBetweenArray.length);
                                    betweenArray[betweenIndex - 1] = String.valueOf(tempBetweenArray);
                                }
                                wordIndexes[0] = wordStart;
                                endIndex = wordStart + startCriteria.length();
                                if (endIndex < userInput.length()) {

                                    wordEnd = userInput.indexOf(endCriteria, endIndex);
                                    if (wordEnd != -1) {

                                        ++wordIndex;
                                        wordsArray = Arrays.copyOf(wordsArray, wordIndex);
                                        startIndex = wordEnd + endCriteria.length();
                                        wordIndexes[1] = startIndex - wordIndexes[0];
                                        prevSymbolIndex = symbolIndex;
                                        symbolIndex = startIndex;
                                        char[] tempWordArray = new char[wordIndexes[1]];
                                        System.arraycopy(charUserInput, wordIndexes[0], tempWordArray, 0, tempWordArray.length);
                                        char[] reversedCharArray = new char[tempWordArray.length];
                                        for (int character = 0; character < tempWordArray.length; character++) {

                                            reversedCharArray[character] = tempWordArray[tempWordArray.length - character - 1];
                                        }
                                        wordsArray[wordIndex - 1] = String.valueOf(reversedCharArray);
                                    }
                                }
                            }
                        }
                        if ((charArrayBeginning.length != 0) && (charArrayEnd.length != 0)) {

                            startEndIndex = 2;
                        } else if ((charArrayBeginning.length != 0) || (charArrayEnd.length != 0)) {

                            startEndIndex = 1;
                        }
                        System.out.println("startEndIndex: " + startEndIndex + " wordsArray.length: " + wordsArray.length + " betweenArray.length: " + betweenArray.length);
                        String[] finalStringArray = new String[startEndIndex + wordsArray.length + betweenArray.length];
                        int index = 0;
                        if (!startString.isBlank()) {

                            finalStringArray[0] = startString;
                            index++;
                        }
                        if (wordsArray.length != 0) {

                            for (int finalArrayIndex = index, wordsArrayIndex = 0; finalArrayIndex < finalStringArray.length && wordsArrayIndex < wordsArray.length; finalArrayIndex += 2, wordsArrayIndex++) {

                                finalStringArray[finalArrayIndex] = wordsArray[wordsArrayIndex];
                            }
                        }
                        for (String word : finalStringArray) {

                            System.out.println("finalStringArray[Index]: " + word);
                        }
                        if (betweenArray.length != 0) {

                            for (int finalArrayIndex = (index + 1), betweenArrayIndex = 0; finalArrayIndex < finalStringArray.length && betweenArrayIndex < betweenArray.length; finalArrayIndex += 2, betweenArrayIndex++) {

                                finalStringArray[finalArrayIndex] = betweenArray[betweenArrayIndex];
                            }
                        }
                        if (!endString.isBlank()) {

                            finalStringArray[finalStringArray.length - 1] = endString;
                        }
                        StringBuilder finalString = new StringBuilder();
                        for (String word : finalStringArray) {

                            finalString.append(word);
                        }
                        return finalString.toString();
                    } else
                    {

                        return "Wrong criteria order";
                    }
                } else
                {

                    return "Invalid criteria";
                }
            }
        } else {

            return "Empty input";
        }
    }
}
