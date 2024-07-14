package com.app.blasters.repo

import com.app.blasters.model.Match
import com.app.blasters.model.Player
import com.app.blasters.utils.NetworkResult

interface BlastersRepo {

    suspend fun getPlayers(): NetworkResult<List<Player>>

    suspend fun getMatches(): NetworkResult<List<Match>>

}