package com.agb.feature_home.ui.transactions

import android.graphics.Canvas
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import androidx.recyclerview.widget.RecyclerView

class StickyHeaderTransactionsDecorator(
    private val adapter: StickyHeaderAdapter
) : RecyclerView.ItemDecoration() {
    private var headerHeight = 0

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)

        val topChild = parent.getChildAt(0)

        val topChildPosition = parent.getChildAdapterPosition(topChild)
        if (topChildPosition == RecyclerView.NO_POSITION) {
            return
        }

        val headerPos = adapter.getHeaderPositionForItem(topChildPosition)
        val currentHeader = adapter.getHeaderViewForItem(headerPos, parent)
        fixLayoutSize(parent, currentHeader)

        getChildInContact(parent, currentHeader.bottom, headerPos)?.also {
            if (adapter.isHeader(parent.getChildAdapterPosition(it))) {
                moveHeader(c, currentHeader, it)
                return
            }
        }

        drawHeader(c, currentHeader)
    }

    private fun drawHeader(c: Canvas, header: View) {
        c.save()
        c.translate(0F, 0F)
        header.draw(c)
        c.restore()
    }

    private fun moveHeader(c: Canvas, header: View, nextHeader: View) {
        c.save()
        c.translate(0F, (nextHeader.top - header.height).toFloat())
        header.draw(c)
        c.restore()
    }

    private fun fixLayoutSize(parent: ViewGroup, view: View) {
        val width = parent.width
        val height = parent.height / 3 // header must occupy at most 1/3 of recycler

        val widthSpec = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY)
        val heightSpec = View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.AT_MOST)

        with(view) {
            measure(widthSpec, heightSpec)
            headerHeight = measuredHeight
            layout(0, 0, measuredWidth, measuredHeight)
        }
    }

    private fun getChildInContact(
        parent: RecyclerView,
        contactPoint: Int,
        currentHeaderPos: Int
    ): View? {
        parent.children.forEachIndexed { i, child ->
            var heightTolerance = 0

            if (currentHeaderPos != i) {
                val isHeader = adapter.isHeader(parent.getChildAdapterPosition(child))
                if (isHeader) {
                    heightTolerance = headerHeight - child.height
                }
            }

            val childBottomPosition: Int = if (child.top > 0) {
                child.bottom + heightTolerance
            } else {
                child.bottom
            }

            if (childBottomPosition > contactPoint) {
                if (child.top <= contactPoint) {
                    return child
                }
            }
        }

        return null
    }

    interface StickyHeaderAdapter {
        fun getHeaderPositionForItem(item: Int): Int
        fun getHeaderViewForItem(pos: Int, parent: ViewGroup): View
        fun isHeader(pos: Int): Boolean
    }
}
