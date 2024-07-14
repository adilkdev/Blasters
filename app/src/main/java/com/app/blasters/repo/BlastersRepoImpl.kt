package com.app.blasters.repo

import com.app.blasters.ApiService
import com.app.blasters.model.Match
import com.app.blasters.model.Player
import com.app.blasters.utils.NetworkResult

class BlastersRepoImpl(
    private val apiService: ApiService
): BlastersRepo {

    override suspend fun getPlayers(): NetworkResult<List<Player>> =
        try {
            val response = apiService.getPlayers()
            NetworkResult.Success(response)
        } catch (e: Exception) {
            NetworkResult.Error(e.message ?: "An unknown error occurred")
        }

    override suspend fun getMatches(): NetworkResult<List<Match>> =
        try { val response = apiService.getMatches()
        NetworkResult.Success(response)
    } catch (e: Exception) {
        NetworkResult.Error(e.message ?: "An unknown error occurred")
    }
}