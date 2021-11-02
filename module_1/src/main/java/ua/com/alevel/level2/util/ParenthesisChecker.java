package ua.com.alevel.level2.util;

public final class ParenthesisChecker {

    private ParenthesisChecker() { }

    private static String[] startSymbol = new String[] {"(", "[", "{"};

    private static String[] endSymbol = new String[] {")", "]", "}"};

    public static int[] lastStartParenthesisIndex(String userInput) {
        int[] criteriaLastIndexArray = new int[startSymbol.length];
        int returnValueIndex = 0;
        for (int index = 0; index < startSymbol.length; index++){
            criteriaLastIndexArray[index] = userInput.lastIndexOf(startSymbol[index]);
            if (index > 0 && criteriaLastIndexArray[index] > criteriaLastIndexArray[returnValueIndex]) {
                returnValueIndex = index;
            }
        }
        return new int[]{(returnValueIndex), criteriaLastIndexArray[returnValueIndex]};
    }

    public static int nearestEndParenthesisIndex(String userInput, int[] lastStartCriteriaIndex) {
        return userInput.indexOf(endSymbol[lastStartCriteriaIndex[0]],lastStartCriteriaIndex[1]);
    }

    public static String cutOutSubstring(String userInput, int startIndex, int endIndex) {
        String startPart = userInput.substring(0,startIndex);
        String endPart = userInput.substring(endIndex + 1);
        return startPart + endPart;
    }
}
