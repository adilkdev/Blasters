package com.app.blasters.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.blasters.databinding.ItemPlayerViewBinding
import com.app.blasters.model.Player

class PlayerAdapter(
    private val _players: List<Player>,
    private val clickListener: PlayerClickListener
): RecyclerView.Adapter<PlayerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
        return PlayerViewHolder(
            ItemPlayerViewBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            clickListener
        )
    }

    override fun getItemCount(): Int = _players.size

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        holder.bind(_players[holder.adapterPosition])
    }

}