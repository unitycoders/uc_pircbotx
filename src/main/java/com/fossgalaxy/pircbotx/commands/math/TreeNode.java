package com.fossgalaxy.pircbotx.commands.math;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TreeNode {
    public final Token data;
    public final List<TreeNode> children;

    public TreeNode(Token data) {
        this.data = data;
        this.children = Collections.emptyList();
    }

    public TreeNode(Token data, TreeNode... childNodes) {
        this.data = data;
        this.children = Arrays.asList(childNodes);
    }

    public boolean isLeaf() {
        return children.isEmpty();
    }

    public String toString() {
        if (isLeaf()) {
            return data.toString();
        } else {
            return String.format("%s[%s]", data, children);
        }
    }

}
