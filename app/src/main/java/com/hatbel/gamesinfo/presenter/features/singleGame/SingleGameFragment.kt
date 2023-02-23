package com.hatbel.gamesinfo.presenter.features.singleGame

import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.hatbel.gamesinfo.R
import com.hatbel.gamesinfo.databinding.FragmentSingleGameBinding
import com.hatbel.gamesinfo.presenter.features.gamesList.FragmentListViewModel
import com.hatbel.gamesinfo.presenter.wrappers.BaseFragment
import kotlinx.coroutines.launch
import org.koin.androidx.navigation.koinNavGraphViewModel


class SingleGameFragment : BaseFragment() {

    private lateinit var binding: FragmentSingleGameBinding
    private val gamesViewModel: FragmentListViewModel by koinNavGraphViewModel(R.id.nav_main)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSingleGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        initObservers()
    }

    private fun initUI() {
        val callback = object : OnBackPressedCallback(true ) {
            override fun handleOnBackPressed() {
                gamesViewModel.clearData()
                findNavController().popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

    private fun initObservers() {
        lifecycleScope.launch {
            gamesViewModel.loader.observe(viewLifecycleOwner) { loader ->
                handle(loader)
            }
        }
        lifecycleScope.launch {
            gamesViewModel.game.observe(viewLifecycleOwner) { game ->
                binding.apply {
                    Glide.with(requireContext())
                        .load(game.map().backgroundImage)
                        .into(binding.gameImageView)
                    gameName.text = game.map().name
                    gameDescriptionText.text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        Html.fromHtml(game.map().description, Html.FROM_HTML_MODE_COMPACT)
                    } else {
                        Html.fromHtml(game.map().description)
                    }
                    gameReleased.text = "Released: ${game.map().released}"
                    gameUpdated.text = "Updated: ${game.map().formUpdate()}"
                    gameLinks.text = game.map().website
                    gamePlaytime.text = "Play time: ${game.map().playtime.toString()}"
                    gameTwitchCount.text = "Twitch count: ${game.map().twitchCount}"
                    gameYTCount.text = "YouTube count: ${game.map().youtubeCount}"
                    gamePlatforms.text = "Platforms: ${game.map().formPlatforms()}"
                }
            }
        }
    }
}