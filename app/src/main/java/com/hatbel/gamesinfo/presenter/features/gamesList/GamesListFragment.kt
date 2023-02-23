package com.hatbel.gamesinfo.presenter.features.gamesList

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.hatbel.gamesinfo.R
import com.hatbel.gamesinfo.data.models.GameUI
import com.hatbel.gamesinfo.databinding.FragmentGamesListBinding
import com.hatbel.gamesinfo.presenter.wrappers.BaseFragment
import com.hatbel.gamesinfo.presenter.wrappers.SimpleTextWatcher
import com.hatbel.gamesinfo.presenter.wrappers.extensions.safeNavigate
import kotlinx.coroutines.launch
import org.koin.androidx.navigation.koinNavGraphViewModel

class GamesListFragment : BaseFragment() {

    private lateinit var binding: FragmentGamesListBinding
    private val gamesViewModel: FragmentListViewModel by koinNavGraphViewModel(R.id.nav_main)
    private lateinit var gamesAdapter: GamesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGamesListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        initObservers()
    }

    private fun initObservers() {
        lifecycleScope.launch {
            gamesViewModel.games.observe(viewLifecycleOwner) {
                gamesAdapter.submitData(lifecycle, it)
            }
        }
        lifecycleScope.launch {
            gamesViewModel.loader.observe(viewLifecycleOwner) { loader ->
                handle(loader)
            }
        }
    }

    private fun initUI() {
        binding.searchBar.editText?.addTextChangedListener(object : SimpleTextWatcher() {
            override fun afterTextChanged(p0: Editable?) {
                gamesViewModel.searchGames(p0.toString())
            }
        })
        val onTierItemClick: (gameUI: GameUI) -> Unit = { game ->
            val action = GamesListFragmentDirections.actionGamesListFragmentToSingleGameFragment()
            findNavController().safeNavigate(action)
            gamesViewModel.getGameById(game.id)
        }
        gamesAdapter = GamesAdapter(requireContext(), onTierItemClick)
        binding.gamesList.layoutManager = LinearLayoutManager(requireContext())
        binding.gamesList.adapter = gamesAdapter

    }
}