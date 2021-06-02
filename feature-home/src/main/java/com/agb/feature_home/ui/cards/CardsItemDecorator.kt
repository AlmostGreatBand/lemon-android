package com.agb.feature_home.ui.cards

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.agb.core_ui.utils.dp

class CardsItemDecorator() : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.left = 24.dp
        outRect.right = 24.dp
    }
}
