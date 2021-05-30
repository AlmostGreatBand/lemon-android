package com.agb.feature_home.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.agb.feature_cards.core.domain.models.Card
import com.agb.feature_home.R

class CardsAdapter : RecyclerView.Adapter<CardsAdapter.CardsViewHolder>() {
    private var data = listOf<Card>()

    override fun getItemCount() = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val item = layoutInflater.inflate(R.layout.item_card, parent, false)
        return CardsViewHolder(item)
    }

    override fun onBindViewHolder(holder: CardsViewHolder, position: Int) {
        holder.bind(data[position])
    }

    fun updateDataSet(cards: List<Card>) {
        val diffUtil = CardsDiffUtils(data, cards)
        val cardsDiffResult = DiffUtil.calculateDiff(diffUtil, false)

        data = cards
        cardsDiffResult.dispatchUpdatesTo(this)
    }

    inner class CardsViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        fun bind(card: Card) {
        }
    }
}
