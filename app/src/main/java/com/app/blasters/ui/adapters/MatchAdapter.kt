package com.app.blasters.ui.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.blasters.databinding.ItemMatchViewBinding
import com.app.blasters.model.Match

class MatchAdapter(private val matches: List<Match>, private val playerId: Int): RecyclerView.Adapter<MatchViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchViewHolder {
        return MatchViewHolder(ItemMatchViewBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return matches.size
    }

    override fun onBindViewHolder(holder: MatchViewHolder, position: Int) {
        holder.bind(matches[holder.adapterPosition], playerId)
    }
}

class MatchViewHolder(
    private val itemMatchViewBinding: ItemMatchViewBinding
): RecyclerView.ViewHolder(itemMatchViewBinding.root) {

    fun bind(match: Match, playerId: Int) {
        itemMatchViewBinding.tvPlayerOne.text = match.playerOne.name
        itemMatchViewBinding.tvPlayerTwo.text = match.playerTwo.name
        itemMatchViewBinding.tvScore.text = "${match.playerOne.score} - ${match.playerTwo.score}"

        if(match.playerOne.id == playerId) {
            if (match.playerOne.score > match.playerTwo.score) {
                itemMatchViewBinding.root.setBackgroundColor(Color.GREEN)
            } else if(match.playerOne.score == match.playerTwo.score) {
                itemMatchViewBinding.root.setBackgroundColor(Color.WHITE)
            } else {
                itemMatchViewBinding.root.setBackgroundColor(Color.RED)
            }
        } else {
            if (match.playerTwo.score > match.playerOne.score) {
                itemMatchViewBinding.root.setBackgroundColor(Color.GREEN)
            } else if(match.playerTwo.score == match.playerOne.score) {
                itemMatchViewBinding.root.setBackgroundColor(Color.WHITE)
            } else {
                itemMatchViewBinding.root.setBackgroundColor(Color.RED)
            }
        }
    }

}