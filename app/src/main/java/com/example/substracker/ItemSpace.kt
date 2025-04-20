package com.example.substracker
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class ItemSpace: RecyclerView.ItemDecoration() {
    private var verticalSpace = 2 // Defines a 2-pixel space between items (can be adjusted).
                                    // It’s the amount of vertical space added below each item.

    //This method is called for each item in the RecyclerView.
    override fun getItemOffsets(outRect: Rect,view: View,parent: RecyclerView,state: RecyclerView.State)
    {
        outRect.bottom = verticalSpace
        /*It modifies the outRect, which defines the spacing around each item.
            By setting outRect.bottom, it adds space below each item.
            So each task card or row in your list will have a 2-pixel gap below it.
          */
    }
}
