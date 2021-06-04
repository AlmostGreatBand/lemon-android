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

class TransactionsAdapter :
    RecyclerView.Adapter<TransactionsAdapter.TransactionsViewHolder>(),
    StickyHeaderTransactionsDecorator.StickyHeaderAdapter {
    private var transactions: List<TransactionRecyclerItem> = listOf()

    override fun getItemCount() = transactions.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionsViewHolder {
        val layout = if (viewType == TransactionRecyclerItem.Type.Date.ordinal) {
            R.layout.item_date
        } else {
            R.layout.item_transaction
        }
        val item = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return TransactionsViewHolder(item)
    }

    override fun getItemViewType(position: Int) = transactions[position].type.ordinal

    override fun onBindViewHolder(holder: TransactionsViewHolder, position: Int) {
        holder.bind(transactions[position])
    }

    fun updateDataSet(newTransactions: List<TransactionRecyclerItem>) {
        val diffUtil = TransactionsDiffUtils(transactions, newTransactions)
        val transactionsDiffResult = DiffUtil.calculateDiff(diffUtil, false)

        transactions = newTransactions
        transactionsDiffResult.dispatchUpdatesTo(this)
    }

    inner class TransactionsViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        private val icon = item.findViewById<ImageView>(R.id.icon)
        private val date = item.findViewById<TextView>(R.id.date)
        private val name = item.findViewById<TextView>(R.id.name)
        private val type = item.findViewById<TextView>(R.id.type)
        private val sum = item.findViewById<TextView>(R.id.sum)

        fun bind(transaction: TransactionRecyclerItem) = when (transaction.type) {
            TransactionRecyclerItem.Type.Date -> bindHeader(transaction)
            TransactionRecyclerItem.Type.Transaction -> bindTransaction(transaction)
        }

        private fun bindTransaction(transaction: TransactionRecyclerItem) {
            transaction.amount?.let {
                val backgroundColor = if (it > 0) R.color.goblin else R.color.cinnabar
                icon.setBackgroundColor(ContextCompat.getColor(itemView.context, backgroundColor))
            }
            name.text = transaction.title
            type.text = transaction.description ?: ""
            sum.text = transaction.amount?.toString() ?: ""
        }

        private fun bindHeader(transaction: TransactionRecyclerItem) {
            date.text = transaction.title
        }
    }

    override fun getHeaderPositionForItem(item: Int): Int {
        for (i in item downTo 0) {
            if (isHeader(i)) {
                return i
            }
        }
        return 0
    }

    override fun getHeaderViewForItem(pos: Int, parent: ViewGroup): View {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_date, parent, false)

        val header = transactions[pos]

        view.setBackgroundColor(ContextCompat.getColor(view.context, R.color.white))
        view.findViewById<TextView>(R.id.date).text = header.title

        return view
    }

    override fun isHeader(pos: Int): Boolean {
        return transactions.getOrNull(pos)?.type == TransactionRecyclerItem.Type.Date
    }
}
