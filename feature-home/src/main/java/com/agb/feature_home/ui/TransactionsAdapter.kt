package com.agb.feature_home.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.agb.feature_home.R
import com.agb.feature_transactions.core.domain.models.Transaction

class TransactionsAdapter : RecyclerView.Adapter<TransactionsAdapter.TransactionsViewHolder>() {
    private var data = listOf<Transaction>()

    override fun getItemCount() = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val item = layoutInflater.inflate(R.layout.item_card, parent, false)
        return TransactionsViewHolder(item)
    }

    override fun onBindViewHolder(holder: TransactionsViewHolder, position: Int) {
        holder.bind(data[position])
    }

    fun updateDataSet(cards: List<Transaction>) {
        val diffUtil = CardsDiffUtils(data, cards)
        val cardsDiffResult = DiffUtil.calculateDiff(diffUtil, false)

        data = cards
        cardsDiffResult.dispatchUpdatesTo(this)
    }
    inner class TransactionsViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        fun bind()
    }
}