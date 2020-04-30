package com.mrlsm.searchtreeview.adapter;

import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mrlsm.searchtreeview.R;
import com.mrlsm.searchtreeview.model.TreeNode;


/**
 * @author Mrlsm
 */
public class TreeNodeBinder extends TreeViewBinder<TreeNodeBinder.ViewHolder> {

    private String keyword;

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public TreeNodeBinder.ViewHolder provideViewHolder(View itemView) {
        return new TreeNodeBinder.ViewHolder(itemView);
    }

    @Override
    public void bindView(TreeNodeBinder.ViewHolder holder, int position, TreeNode node) {
        holder.ivArrow.setRotation(0);
        holder.ivArrow.setImageResource(R.mipmap.icon_black_right);
        int rotateDegree = node.isExpand() ? 90 : 0;
        holder.ivArrow.setRotation(rotateDegree);
        holder.ivArrow.setVisibility(node.isLeaf() || node.getChildren() == null || node.getChildren().size() < 1
                ? View.INVISIBLE : View.VISIBLE);
        //如果存在搜索关键字
        if (!TextUtils.isEmpty(keyword) && node.getName().contains(keyword)) {
            int index = node.getName().indexOf(keyword);
            int len = keyword.length();
            Spanned temp = Html.fromHtml(node.getName().substring(0, index)
                    + "<font color=#FF0000>" + node.getName().substring(index, index + len) + "</font>"
                    + node.getName().substring(index + len));

            holder.tvName.setText(temp);
        } else {
            holder.tvName.setText(node.getName());
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_tree_view_node;
    }

    public class ViewHolder extends TreeViewBinder.ViewHolder {
        private ImageView ivArrow;
        private TextView tvName;

        public ViewHolder(View rootView) {
            super(rootView);
            this.ivArrow = (ImageView) rootView.findViewById(R.id.iv_arrow);
            this.tvName = (TextView) rootView.findViewById(R.id.tv_name);
        }

        public ImageView getIvArrow() {
            return ivArrow;
        }

        public TextView getTvName() {
            return tvName;
        }
    }
}