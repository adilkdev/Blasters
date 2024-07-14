package com.app.blasters.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.blasters.ui.adapters.MatchAdapter
import com.app.blasters.utils.NetworkResult
import com.app.blasters.databinding.FragmentMatchesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MatchesFragment: Fragment() {

    private var _binding: FragmentMatchesBinding? = null
    private val binding get() = _binding!!

    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMatchesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val playerId = arguments?.getInt("playerId")
        val players = (sharedViewModel.playersResponse.value as NetworkResult.Success).data

        val matches = (sharedViewModel.matchesResponse.value as NetworkResult.Success).data?.filter {
            it.playerOne.id == playerId || it.playerTwo.id == playerId
        }
        matches?.map { match ->
            match.playerOne.name = players?.find { match.playerOne.id == it.id }?.name ?: ""
            match.playerTwo.name = players?.find { match.playerTwo.id == it.id }?.name ?: ""
        }

        binding.recyclerViewMatches.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.recyclerViewMatches.adapter = matches?.let { MatchAdapter(it, playerId!!) }
    }

}