package com.pg13.mycitchen.ui.rv

import android.graphics.Rect
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration

class VerticalSpaceItemDecoration(private val mVerticalSpaceHeight: Int) :
    ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, itemPosition: Int, parent: RecyclerView) {
        if (parent.adapter != null && itemPosition != parent.adapter!!.itemCount - 1) {
            outRect.bottom = mVerticalSpaceHeight
        }
    }
}