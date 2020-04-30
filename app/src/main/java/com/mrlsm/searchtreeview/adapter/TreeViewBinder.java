package com.mrlsm.searchtreeview.adapter;

import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.mrlsm.searchtreeview.model.TreeNode;

/**
 * @author Mrlsm
 */
public abstract class TreeViewBinder<VH extends RecyclerView.ViewHolder> {
    public abstract VH provideViewHolder(View itemView);

    public abstract void bindView(VH holder, int position, TreeNode node);

    public abstract int getLayoutId();

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View rootView) {
            super(rootView);
        }

        protected <T extends View> T findViewById(@IdRes int id) {
            return (T) itemView.findViewById(id);
        }
    }

}
