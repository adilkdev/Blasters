package com.app.blasters

import com.app.blasters.model.Match
import com.app.blasters.model.Player
import retrofit2.http.GET

interface ApiService {

    @GET("b/IKQQ")
    suspend fun getPlayers(): List<Player>

    @GET("b/JNYL")
    suspend fun getMatches(): List<Match>

}