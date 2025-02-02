package com.agb.feature_home.ui.transactions

import androidx.recyclerview.widget.DiffUtil

class TransactionsDiffUtils(
    private val old: List<TransactionRecyclerItem>,
    private val new: List<TransactionRecyclerItem>,
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
