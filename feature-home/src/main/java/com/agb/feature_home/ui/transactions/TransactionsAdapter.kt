package com.agb.feature_home.ui.transactions

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.agb.feature_home.R
import com.agb.feature_transactions.core.domain.models.Transaction

class TransactionsAdapter : RecyclerView.Adapter<TransactionsAdapter.TransactionsViewHolder>() {
    private var transactions: List<Transaction> = listOf()

    override fun getItemCount() = transactions.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionsViewHolder {
        val li = LayoutInflater.from(parent.context)
        val item = li.inflate(R.layout.item_transaction, parent, false)
        return TransactionsViewHolder(item)
    }

    override fun onBindViewHolder(holder: TransactionsViewHolder, position: Int) {
        holder.bind(transactions[position])
    }

    fun updateDataSet(newTransactions: List<Transaction>) {
        val diffUtil = TransactionsDiffUtils(transactions, newTransactions)
        val transactionsDiffResult = DiffUtil.calculateDiff(diffUtil, false)

        transactions = newTransactions
        transactionsDiffResult.dispatchUpdatesTo(this)
    }

    inner class TransactionsViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        private val icon = item.findViewById<ImageView>(R.id.icon)
        private val name = item.findViewById<TextView>(R.id.name)
        private val type = item.findViewById<TextView>(R.id.type)
        private val sum = item.findViewById<TextView>(R.id.sum)

        fun bind(transaction: Transaction) {
            val backgroundColor = if (transaction.amount > 0) R.color.goblin else R.color.cinnabar
            icon.setBackgroundColor(ContextCompat.getColor(itemView.context, backgroundColor))
            type.text = transaction.type
            sum.text = transaction.amount.toString()
        }
    }
}
