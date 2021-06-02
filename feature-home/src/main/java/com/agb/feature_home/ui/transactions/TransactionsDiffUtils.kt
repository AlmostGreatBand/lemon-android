package com.agb.feature_home.ui.transactions

import androidx.recyclerview.widget.DiffUtil
import com.agb.feature_transactions.core.domain.models.Transaction

class TransactionsDiffUtils(
    private val old: List<Transaction>,
    private val new: List<Transaction>,
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
