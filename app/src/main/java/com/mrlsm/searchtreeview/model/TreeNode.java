package com.mrlsm.searchtreeview.model;

import java.io.Serializable;
import java.util.List;

/**
 * @author Mrlsm
 */
public class TreeNode implements Serializable {

    private String id;
    private String name;
    private String parentId;
    private int level;
    private boolean leaf;
    private List<TreeNode> children;

    private boolean isExpand;
    private boolean isVisible = true;
    private boolean isChecked = false;

    public boolean isLeaf() {
        return leaf;
    }

    public int getHeight() {
        return level - 1;
    }

    public boolean toggle() {
        isExpand = !isExpand;
        return isExpand;
    }

    public void collapse() {
        if (isExpand) {
            isExpand = false;
        }
    }

    public void collapseAll() {
        if (children == null || children.isEmpty()) {
            return;
        }
        for (TreeNode child : this.children) {
            child.collapseAll();
        }
    }

    public void expand() {
        if (!isExpand) {
            isExpand = true;
        }
    }

    public void expandAll() {
        expand();
        if (children == null || children.isEmpty()) {
            return;
        }
        for (TreeNode child : this.children) {
            child.expandAll();
        }
    }

    public boolean isExpand() {
        return isExpand;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public List<TreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<TreeNode> children) {
        this.children = children;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    public void setLeaf(boolean leaf) {
        this.leaf = leaf;
    }

    public boolean isChecked() {
        return isChecked && isLeaf();
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
