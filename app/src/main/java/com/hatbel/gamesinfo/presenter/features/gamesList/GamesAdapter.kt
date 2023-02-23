package com.hatbel.gamesinfo.presenter.features.gamesList

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hatbel.gamesinfo.data.models.GameUI
import com.hatbel.gamesinfo.databinding.ItemGameBinding

class GamesAdapter(
    private val context: Context,
    private val onTierItemClick: (GameUI) -> Unit
) : PagingDataAdapter<GameUI, GamesAdapter.StockViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StockViewHolder {
        val binding = ItemGameBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StockViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StockViewHolder, position: Int) {
        holder.bindItem(getItem(position))
    }

    inner class StockViewHolder(private val binding: ItemGameBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindItem(game: GameUI?) {
            binding.name.text = game?.name
            Glide.with(context)
                .load(game?.backgroundImage)
                .into(binding.gameImage)
            binding.ratings.text = "Rating: ${game?.rating.toString()} / 5"
            binding.moreInfoButton.setOnClickListener{
                game?.let { it1 -> onTierItemClick.invoke(it1) }
            }
        }
    }
}

object DiffCallback : DiffUtil.ItemCallback<GameUI>() {
    override fun areItemsTheSame(oldItem: GameUI, newItem: GameUI): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: GameUI, newItem: GameUI): Boolean =
        oldItem == newItem
}