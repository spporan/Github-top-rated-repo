package poran.cse.github_top_rated_repo.util

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class ItemDecoration(
    private val firstItemTopMargin: Int,
    private val lastItemBottomMargin: Int,
): RecyclerView.ItemDecoration()  {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        if (position == 0) {
            outRect.top = firstItemTopMargin
        } else if (position == (parent.adapter?.itemCount ?: 0) - 1)
            outRect.bottom = lastItemBottomMargin
        }

}