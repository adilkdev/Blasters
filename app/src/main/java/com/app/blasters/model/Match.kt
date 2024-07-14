package com.app.blasters.model

import com.google.gson.annotations.SerializedName

data class Match(
    val matchNo: Int,

    @SerializedName("player1")
    val playerOne: PlayerScore,

    @SerializedName("player2")
    val playerTwo: PlayerScore
)
