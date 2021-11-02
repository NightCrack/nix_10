package ua.com.alevel.level2;

import ua.com.alevel.level2.entity.TreeNode;
import ua.com.alevel.level2.util.Generator;
import ua.com.alevel.level2.util.TreeNester;

import java.util.Scanner;

public class BinaryTreeDepth {

    private final Scanner userInput = new Scanner(System.in);
    private final String EXIT_VALUE = "0";
    private String EVALUATOR = "1";
    private String RANDOMIZER_INITIATION = "random";
    private int treeDepth;

    public void check() {
        System.out.println();
        System.out.println("Input tree depth value.");
        System.out.println("Input '" + RANDOMIZER_INITIATION
                + "' to randomize the depth of tree.");
        System.out.println("Input '"
                + EXIT_VALUE + "' to exit the sub-level.");

        do {
            System.out.print("Your input: ");
            String input = userInput.nextLine();
            if (input.isBlank()) {
                System.out.println("Blank input.");
            } else {
                if (input.equals(EXIT_VALUE)) {
                    EVALUATOR = EXIT_VALUE;
                } else {
                    if (input.equals(RANDOMIZER_INITIATION)) {
                        treeDepth = (Generator.generateValue() + 1);
                    } else if (!checkIfNumber(input)) {
                        System.out.println("Incorrect input.");
                        continue;
                    }
                    if (!input.equals(RANDOMIZER_INITIATION)) {
                        treeDepth = Integer.parseInt(input);
                    }
                    TreeNode[][] outTree = TreeNester.buildTree(treeDepth);
                    String[][] outTreeString = TreeNester.buildTreeToString(outTree);
                    for (int indexA = 0; indexA < outTreeString.length; indexA++) {
                        System.out.print("Grade " + (indexA + 1) + ": ");
                        for (int indexB = 0; indexB < outTreeString[indexA].length; indexB++) {
                            System.out.print(outTreeString[indexA][indexB] + " ");
                        }
                        System.out.println();
                    }
                    for (int index = 0; index < outTree[0].length; index++) {
                        if (outTree[0][index] != null) {
                            System.out.println("Tree depth: " + Generator.getTreeDepth(outTree[0][index]));
                            break;
                        }
                    }
                }
            }
        } while (EVALUATOR.equals(EXIT_VALUE));
    }

    private boolean checkIfNumber(String userInput) {
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
        if (arraySum != 0 && (numberOfElements == inputCharArray.length)) {
            return true;
        } else {
            return false;
        }
    }
}
