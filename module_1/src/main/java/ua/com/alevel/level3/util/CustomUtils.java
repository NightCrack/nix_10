package ua.com.alevel.level3.util;


public final class CustomUtils {

    private CustomUtils() {
    }

    public static int[][] buildRandom(int amountOfRows, int amountOfElements) {
        int[][] returnMatrix = new int[amountOfRows][amountOfElements];
        for (int indexA = 0; indexA < returnMatrix.length; indexA++) {
            for (int indexB = 0; indexB < returnMatrix[indexA].length; indexB++) {
                returnMatrix[indexA][indexB] = setState();
            }
        }
        return returnMatrix;
    }

    public static int[][] nextGen(int[][] currentGen) {
        int[][] resultMatrix = currentGen;
        for (int indexA = 0; indexA < currentGen.length; indexA++) {
            for (int indexB = 0; indexB < currentGen[indexA].length; indexB++) {
                int[] elementPosition = {indexA, indexB};
                resultMatrix[indexA][indexB] = CustomUtils.evaluator(currentGen[indexA][indexB], CustomUtils.neighbours(currentGen, elementPosition));
            }
        }
        return resultMatrix;
    }

    private static int setState() {
        return (int) (Math.round(Math.random()));
    }

    public static int evaluator(int element, int[] neighbours) {
        int returnValue = 0;
        int sumOfNeighbours = 0;
        for (int index = 0; index < neighbours.length; index++) {
            sumOfNeighbours += neighbours[index];
        }
        if (element == 0) {
            if (sumOfNeighbours == 3) {
                returnValue = 1;
            }
        }
        if (element == 1) {
            if ((sumOfNeighbours < 2) || (sumOfNeighbours > 3)) {
                returnValue = 0;
            } else {
                returnValue = 1;
            }
        }
        return returnValue;
    }

    public static int[] neighbours(int[][] workArray, int[] elementPosition) {
        int[] returnArray = new int[8];
        int[] neighbourCoordinates = new int[2];
        for (int indexA = 0; indexA < 8; indexA++) {
            for (int indexB = 0; indexB < neighbourCoordinates.length; indexB++) {
                neighbourCoordinates[indexB] = elementPosition[indexB] + positionModifier(indexA)[indexB];
            }
            if ((neighbourCoordinates[0] < 0) || (neighbourCoordinates[1] < 0) || !(neighbourCoordinates[0] < workArray.length) || !(neighbourCoordinates[1] < workArray[0].length)) {
                returnArray[indexA] = 0;
            } else {
                returnArray[indexA] = workArray[neighbourCoordinates[0]][neighbourCoordinates[1]];
            }
        }
        return returnArray;
    }

    private static int[] positionModifier(int modifierNumber) {
        int[] position0 = {0, 1};
        int[] position1 = {1, 1};
        int[] position2 = {1, 0};
        int[] position3 = {1, -1};
        int[] position4 = {0, -1};
        int[] position5 = {-1, -1};
        int[] position6 = {-1, 0};
        int[] position7 = {-1, 1};
        int[] workArray = new int[2];
        switch (modifierNumber) {
            case 0:
                workArray = position0;
                break;
            case 1:
                workArray = position1;
                break;
            case 2:
                workArray = position2;
                break;
            case 3:
                workArray = position3;
                break;
            case 4:
                workArray = position4;
                break;
            case 5:
                workArray = position5;
                break;
            case 6:
                workArray = position6;
                break;
            case 7:
                workArray = position7;
                break;
        }
        return workArray;
    }

    public static boolean checkIfNumber(String userInput) {
        char[] inputCharArray = userInput.toCharArray();
        int[] transferArray = new int[inputCharArray.length];
        int numberOfElements = 0;
        for (int i = 0; i < inputCharArray.length; i++) {
            if (inputCharArray[i] <= '9' && inputCharArray[i] >= '0') {
                transferArray[i] = Character.getNumericValue(inputCharArray[i]) + 1;
                numberOfElements++;
            } else {
                transferArray[i] = 0;
            }
        }
        int arraySum = 0;
        for (int i = 0; i < transferArray.length; i++) {
            arraySum += transferArray[i];
        }
        return arraySum != 0 && (numberOfElements == inputCharArray.length);
    }
}
