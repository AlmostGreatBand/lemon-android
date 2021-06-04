package com.agb.feature_home.ui.transactions

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.agb.feature_cards.core.domain.models.Card
import com.agb.feature_home.R
import com.agb.feature_transactions.core.domain.models.Transaction
import java.util.*

class TransactionsPageAdapter :
    RecyclerView.Adapter<TransactionsPageAdapter.TransactionsPageViewHolder>() {
    private var cards: List<Card> = listOf()
    private var transactions: Map<Card, List<Transaction>> = mapOf()

    override fun getItemCount() = cards.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionsPageViewHolder {
        val li = LayoutInflater.from(parent.context)
        val item = li.inflate(R.layout.item_transaction_page, parent, false)
        return TransactionsPageViewHolder(item)
    }

    override fun onBindViewHolder(holder: TransactionsPageViewHolder, position: Int) {
        val card = cards[position]
        holder.bind(transactions[card] ?: listOf())
    }

    fun updateDataSet(newCards: List<Card>, newTransactions: Map<Card, List<Transaction>>) {
        cards = newCards
        transactions = newTransactions

        // TODO replace with diff utils in next version
        notifyDataSetChanged()
    }

    inner class TransactionsPageViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        private val recyclerView = item.findViewById<RecyclerView>(R.id.recycler)

        fun bind(transactions: List<Transaction>) {
            val adapter = TransactionsAdapter()
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(itemView.context)
            recyclerView.addItemDecoration(StickyHeaderTransactionsDecorator(adapter))

            adapter.updateDataSet(List(10) { if ((0..1).random() == 0) transactions[0] else transactions[0].copy(date = Date(121, 5, 3)) }.toTransactionRecyclerItems())
        }
    }
}
