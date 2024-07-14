package com.app.blasters.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.blasters.model.Match
import com.app.blasters.utils.NetworkResult
import com.app.blasters.model.Player
import com.app.blasters.ui.adapters.PlayerAdapter
import com.app.blasters.ui.adapters.PlayerClickListener
import com.app.blasters.R
import com.app.blasters.databinding.FragmentPointsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PointsFragment: Fragment(), PlayerClickListener {

    private lateinit var binding: FragmentPointsBinding

    private val sharedViewModel: SharedViewModel by activityViewModels()

    private lateinit var playerAdapter: PlayerAdapter

    private var _matches = listOf<Match>()

    private val playersObserver = Observer<NetworkResult<List<Player>>> {
        when(it) {
            is NetworkResult.Loading -> Unit
            is NetworkResult.Success -> {
                it.data?.let { players ->
                    players.forEach {
                        it.points = sharedViewModel.calculatePlayerPoints(it.id, _matches)
                    }
                    playerAdapter = PlayerAdapter(players.sortedByDescending { it.points }, this)
                    binding.recyclerViewPoints.adapter = playerAdapter
                    val position = (binding.recyclerViewPoints.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                    if(position != -1 && position != sharedViewModel.scrollPosition)
                        binding.recyclerViewPoints.smoothScrollToPosition(sharedViewModel.scrollPosition)
                }
            }
            is NetworkResult.Error -> Unit
        }
    }

    private val matchesObserver = Observer<NetworkResult<List<Match>>> {
        when(it) {
            is NetworkResult.Loading -> Unit
            is NetworkResult.Success -> {
                println("matches observer")
                it.data?.let { matches ->
                    _matches = matches
                    sharedViewModel.getPlayers()
                }
            }
            is NetworkResult.Error -> Unit
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPointsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerViewPoints.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        sharedViewModel.playersResponse.observe(viewLifecycleOwner, playersObserver)
        sharedViewModel.matchesResponse.observe(viewLifecycleOwner, matchesObserver)
        sharedViewModel.getMatches()
    }

    override fun onStop() {
        super.onStop()
        sharedViewModel.playersResponse.removeObserver(playersObserver)
        sharedViewModel.matchesResponse.removeObserver(matchesObserver)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        sharedViewModel.scrollPosition = (binding.recyclerViewPoints.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
    }

    override fun onClick(playerId: Int) {
        val bundle = Bundle()
        bundle.putInt("playerId", playerId)
        findNavController().navigate(R.id.action_pointsFragment_to_matchesFragment, bundle)
    }

}