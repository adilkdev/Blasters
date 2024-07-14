package com.app.blasters.ui

import android.os.Parcelable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.blasters.repo.BlastersRepo
import com.app.blasters.model.Match
import com.app.blasters.utils.NetworkResult
import com.app.blasters.model.Player
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val repo: BlastersRepo
): ViewModel() {

    var scrollPosition = 0

    private val _playersResponse: MutableLiveData<NetworkResult<List<Player>>> = MutableLiveData()
    val playersResponse: LiveData<NetworkResult<List<Player>>> = _playersResponse

    fun getPlayers() {
        println("getPlayers()")
        _playersResponse.postValue(NetworkResult.Loading())
        viewModelScope.launch(Dispatchers.IO) {
            _playersResponse.postValue(repo.getPlayers())
        }
    }

    private val _matchesResponse: MutableLiveData<NetworkResult<List<Match>>> = MutableLiveData()
    val matchesResponse: LiveData<NetworkResult<List<Match>>> = _matchesResponse

    fun getMatches() {
        println("getMatches()")
        _matchesResponse.postValue(NetworkResult.Loading())
        viewModelScope.launch(Dispatchers.IO) {
            _matchesResponse.postValue(repo.getMatches())
        }
    }

    fun calculatePlayerPoints(playerId: Int, matches: List<Match>): Int {
        var points = 0

        for (match in matches) {
            val playerOne = match.playerOne
            val playerTwo = match.playerTwo

            when {
                playerOne.id == playerId && playerOne.score > playerTwo.score -> points += 3
                playerOne.id == playerId && playerOne.score == playerTwo.score -> points += 1
                playerOne.id == playerId && playerOne.score < playerTwo.score -> points += 0

                playerTwo.id == playerId && playerTwo.score > playerOne.score -> points += 3
                playerTwo.id == playerId && playerTwo.score == playerOne.score -> points += 1
                playerTwo.id == playerId && playerTwo.score < playerOne.score -> points += 0
            }
        }

        return points
    }

}