package com.app.blasters.model

data class Player(
    val id: Int,
    val name: String,
    val icon: String,
    var points: Int = 0
)
