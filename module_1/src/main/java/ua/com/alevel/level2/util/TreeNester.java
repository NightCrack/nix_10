package ua.com.alevel.level2.util;

import ua.com.alevel.level2.entity.TreeNode;

import java.math.BigDecimal;

public final class TreeNester {

    private TreeNester() {
    }

    private static TreeNode nestNode(TreeNode parentNode) {
        TreeNode newNode = new TreeNode(Generator.generateValue());
        if (Generator.setBranchesAmount() == 1) {
            if (Generator.setBranchesAmount() == 1) {
                parentNode.setLeft(newNode);
            } else {
                parentNode.setRight(newNode);
            }
        } else {
            parentNode.setLeft(newNode);
            parentNode.setRight(new TreeNode(Generator.generateValue()));
        }
        return parentNode;
    }

    public static TreeNode[][] buildTree(int treeDepth) {
        BigDecimal amountOfBranchesPerNode = new BigDecimal("2");
        int numberOfElements = amountOfBranchesPerNode.pow(treeDepth).subtract(new BigDecimal(1)).intValue();
        TreeNode[][] treeArray = new TreeNode[treeDepth][numberOfElements];
        int[][] treeElementIndexes = treeElementIndexes(treeArray.length);
        TreeNode parentNode = new TreeNode(Generator.generateValue());
        TreeNode currentParentNode;
        TreeNode[] currentChildrenArray = new TreeNode[2];
        for (int indexA = 0; indexA < treeArray.length; indexA++) {
            int previousRow = indexA - 1;
            if (previousRow >= 0) {
                for (int indexC = 0, nextIterationStartingElement; indexC < treeElementIndexes.length; indexC++) {
                    if (treeElementIndexes[previousRow][indexC] == 0) {
                        break;
                    } else {
                        nextIterationStartingElement = indexC * 2;
                        currentParentNode = treeArray[previousRow][treeElementIndexes[previousRow][indexC] - 1];
                        if (currentParentNode != null) {
                            nestNode(currentParentNode);
                            currentChildrenArray[0] = currentParentNode.getLeft();
                            currentChildrenArray[1] = currentParentNode.getRight();
                        } else {
                            currentChildrenArray[0] = null;
                            currentChildrenArray[1] = null;
                        }
                        for (int indexD = 0; indexD < currentChildrenArray.length; indexD++) {
                            treeArray[indexA][treeElementIndexes[indexA][nextIterationStartingElement + indexD] - 1] = currentChildrenArray[indexD];
                        }
                    }
                }
            } else {
                currentParentNode = parentNode;
                treeArray[indexA][treeElementIndexes[indexA][indexA] - 1] = currentParentNode;
            }
        }
        return treeArray;
    }

    public static String[][] buildTreeToString(TreeNode[][] builtTreeArray) {
        int maxLength = 1;
        for (int indexA = 0; indexA < builtTreeArray.length; indexA++) {
            for (int indexB = 0; indexB < builtTreeArray[indexA].length; indexB++) {
                if (builtTreeArray[indexA][indexB] != null) {
                    int currentLength = builtTreeArray[indexA][indexB].toString().length();
                    if (currentLength > maxLength) {
                        maxLength = currentLength;
                    }
                }
            }
        }
        int segmentLength = maxLength * 2;
        String[][] returnArray = new String[builtTreeArray.length][builtTreeArray[0].length];
        for (int indexA = 0; indexA < returnArray.length; indexA++) {
            for (int indexB = 0; indexB < returnArray[indexA].length; indexB++) {
                if (builtTreeArray[indexA][indexB] != null) {
                    returnArray[indexA][indexB] = rowSegmentBuilder(builtTreeArray[indexA][indexB].toString(), segmentLength);
                } else {
                    returnArray[indexA][indexB] = rowSegmentBuilder("", segmentLength);
                }
            }
        }
        return returnArray;
    }

    private static int[][] treeElementIndexes(int depth) {
        int[][] returnArray = new int[depth][treeWidth(depth)];
        for (int indexA = 0, multiplier = depth; indexA < returnArray.length; indexA++) {
            --multiplier;
            int rowValueMultiplier = (int) Math.pow(2, multiplier);
            for (int indexB = 0; indexB < returnArray[indexA].length; indexB++) {
                int rowElementPosition = rowValueMultiplier * (2 * indexB + 1);
                if (rowElementPosition <= returnArray[indexA].length) {
                    returnArray[indexA][indexB] = rowElementPosition;
                } else {
                    break;
                }
            }
        }
        return returnArray;
    }

    private static int treeWidth(int treeDepth) {
        BigDecimal amountOfBranchesPerNode = new BigDecimal("2");
        int returnValue = amountOfBranchesPerNode.pow(treeDepth).subtract(new BigDecimal(1)).intValue();
        return returnValue;
    }

    private static String rowSegmentBuilder(String inputValue, int segmentLength) {
        String nullBrick = "-";
        for (int index = 0; index < (segmentLength - inputValue.length()); index++) {
            nullBrick += "-";
        }
        return nullBrick + inputValue;
    }
}
