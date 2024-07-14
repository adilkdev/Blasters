package com.app.blasters.ui.adapters

import androidx.recyclerview.widget.RecyclerView
import com.app.blasters.databinding.ItemPlayerViewBinding
import com.app.blasters.model.Player
import com.bumptech.glide.Glide

class PlayerViewHolder(
    private val binding: ItemPlayerViewBinding,
    private val clickListener: PlayerClickListener
): RecyclerView.ViewHolder(binding.root) {

    fun bind(player: Player) {
        binding.tvPlayerName.text = player.name
        binding.tvPoints.text = player.points.toString()
        Glide.with(binding.root.context)
            .load(player.icon)
            .into(binding.ivIcon)

        binding.root.setOnClickListener {
            clickListener.onClick(player.id)
        }
    }

}