package com.agb.feature_home.ui

import androidx.recyclerview.widget.DiffUtil
import com.agb.feature_cards.core.domain.models.Card

class CardsDiffUtils(
    private val old: List<Card>,
    private val new: List<Card>,
) : DiffUtil.Callback() {
    override fun getOldListSize() = old.size

    override fun getNewListSize() = new.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return old[oldItemPosition] === new[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return old[oldItemPosition] == new[newItemPosition]
    }
}
