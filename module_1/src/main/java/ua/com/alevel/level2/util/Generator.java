package ua.com.alevel.level2.util;

import ua.com.alevel.level2.entity.TreeNode;

public final class Generator {

    private Generator() { }

    public static int generateValue() {
        return (int) (Math.random() * 9);
    }

    public static int setBranchesAmount() {
        return (int) (1 + Math.round(Math.random()));
    }

    public static int getTreeDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int left = getTreeDepth(root.getLeft());
        int right = getTreeDepth(root.getRight());
        return Math.max(left,right) + 1;
    }


}
