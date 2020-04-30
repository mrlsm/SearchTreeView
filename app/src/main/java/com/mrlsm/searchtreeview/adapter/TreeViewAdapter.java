package com.mrlsm.searchtreeview.adapter;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mrlsm.searchtreeview.model.TreeNode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mrlsm
 */
public class TreeViewAdapter<VB extends TreeViewBinder> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String KEY_IS_EXPAND = "IS_EXPAND";
    private final VB viewBinder;
    // 展示的节点集合
    private List<TreeNode> displayNodes;
    private int padding = 30;
    private OnTreeNodeListener onTreeNodeListener;
    private boolean toCollapseChild;
    private String keyword = "";

    public TreeViewAdapter(List<TreeNode> nodes, VB viewBinder) {
        displayNodes = new ArrayList<>();
        if (nodes != null) {
            findDisplayNodes(nodes);
        }
        this.viewBinder = viewBinder;
    }

    /**
     * 从nodes的结点中寻找展开了的非叶结点，添加到displayNodes中。
     *
     * @param nodes 基准点
     */
    private void findDisplayNodes(List<TreeNode> nodes) {
        for (TreeNode node : nodes) {
            if (node.isVisible() && isShowNode(node, new ArrayList<>()).size() > 0) {
                displayNodes.add(node);
            }
            if (!node.isLeaf() && node.isExpand()) {
                findDisplayNodes(node.getChildren());
            }
        }
    }

    private List<TreeNode> isShowNode(TreeNode node, List<TreeNode> list) {
        if (node != null) {
            if (node.isLeaf() && node.getName().contains(keyword)) {
                list.add(node);
            } else if (node.getChildren() != null) {
                for (TreeNode item : node.getChildren()) {
                    isShowNode(item, list);
                }
            }
        }
        return list;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(viewBinder.getLayoutId(), parent, false);
        return viewBinder.provideViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position, List<Object> payloads) {
        if (payloads != null && !payloads.isEmpty()) {
            Bundle b = (Bundle) payloads.get(0);
            for (String key : b.keySet()) {
                switch (key) {
                    case KEY_IS_EXPAND:
                        if (onTreeNodeListener != null) {
                            onTreeNodeListener.onToggle(b.getBoolean(key), holder);
                        }
                        break;
                    default:
                }
            }
        }
        super.onBindViewHolder(holder, position, payloads);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            holder.itemView.setPaddingRelative(displayNodes.get(position).getHeight() * padding, 3, 3, 3);
        } else {
            holder.itemView.setPadding(displayNodes.get(position).getHeight() * padding, 3, 3, 3);
        }
        holder.itemView.setOnClickListener(v -> {
            TreeNode selectedNode = displayNodes.get(holder.getLayoutPosition());
            try {
                long lastClickTime = (long) holder.itemView.getTag();
                if (System.currentTimeMillis() - lastClickTime < 500)
                    return;
            } catch (Exception e) {
                holder.itemView.setTag(System.currentTimeMillis());
            }
            holder.itemView.setTag(System.currentTimeMillis());

            if (onTreeNodeListener != null && onTreeNodeListener.onClick(selectedNode, holder)) {
                return;
            }
            if (selectedNode.isLeaf()) {
                for (TreeNode node : displayNodes) {
                    node.setChecked(false);
                }
                selectedNode.setChecked(true);
                notifyDataSetChanged();
                return;
            }

            if (!isCanExpand(selectedNode)) {
                return;
            }

            boolean isExpand = selectedNode.isExpand();
            int positionStart = displayNodes.indexOf(selectedNode) + 1;
            if (!isExpand) {
                notifyItemRangeInserted(positionStart, addChildNodes(selectedNode, positionStart));
            } else {
                notifyItemRangeRemoved(positionStart, removeChildNodes(selectedNode, true));
            }
        });
        viewBinder.bindView(holder, position, displayNodes.get(position));
    }

    public void setInitializeView() {
        for (TreeNode node : displayNodes) {
            node.setChecked(false);
        }
        notifyDataSetChanged();
    }

    public boolean isCanExpand(TreeNode node) {
        if (node.isLeaf()) {
            return false;
        }
        if (node.getChildren() == null || node.getChildren().size() < 1) {
            return false;
        }
        int cSize = 0;
        for (TreeNode item : node.getChildren()) {
            if (item.isVisible()) {
                cSize++;
            }
        }
        return cSize > 0;
    }

    private int addChildNodes(TreeNode pNode, int startIndex) {
        List<TreeNode> childList = pNode.getChildren();
        int addChildCount = 0;
        if (childList == null) {
            return addChildCount;
        }
        for (TreeNode treeNode : childList) {
            if (treeNode.isVisible() && isShowNode(treeNode, new ArrayList<>()).size() > 0) {
                displayNodes.add(startIndex + addChildCount++, treeNode);
            }
            if (treeNode.isExpand()) {
                addChildCount += addChildNodes(treeNode, startIndex + addChildCount);
            }
        }
        if (!pNode.isExpand()) {
            pNode.toggle();
        }
        return addChildCount;
    }

    private int removeChildNodes(TreeNode pNode) {
        return removeChildNodes(pNode, true);
    }

    private int removeChildNodes(TreeNode pNode, boolean shouldToggle) {
        if (pNode.isLeaf()) {
            return 0;
        }
        List<TreeNode> childList = pNode.getChildren();
        int removeChildCount = childList.size();
        displayNodes.removeAll(childList);
        for (TreeNode child : childList) {
            if (child.isExpand()) {
                if (toCollapseChild) {
                    child.toggle();
                }
                removeChildCount += removeChildNodes(child, false);
            }
        }
        if (shouldToggle) {
            pNode.toggle();
        }
        return removeChildCount;
    }

    @Override
    public int getItemCount() {
        return displayNodes == null ? 0 : displayNodes.size();
    }

    public void setPadding(int padding) {
        this.padding = padding;
    }

    public void ifCollapseChildWhileCollapseParent(boolean toCollapseChild) {
        this.toCollapseChild = toCollapseChild;
    }

    public void setOnTreeNodeListener(OnTreeNodeListener onTreeNodeListener) {
        this.onTreeNodeListener = onTreeNodeListener;
    }

    public interface OnTreeNodeListener {
        /**
         * 单击TreeNodes时调用。
         */
        boolean onClick(TreeNode node, RecyclerView.ViewHolder holder);

        /**
         * 切换TreeNode时调用。
         *
         * @param isExpand 切换后TreeNode的状态。
         */
        void onToggle(boolean isExpand, RecyclerView.ViewHolder holder);
    }

    public void refresh(List<TreeNode> treeNodes, String keyword) {
        this.keyword = keyword;
        displayNodes.clear();
        findDisplayNodes(treeNodes);
        notifyDataSetChanged();
    }
}