package com.hatbel.gamesinfo.presenter.features.gamesList

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.hatbel.gamesinfo.data.models.GameDetailsUI
import com.hatbel.gamesinfo.data.repositories.ResponseResult
import com.hatbel.gamesinfo.domain.usecase.GetGameByIdUseCase
import com.hatbel.gamesinfo.domain.usecase.GetGamesListUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FragmentListViewModel(
    private val getGamesListUseCase: GetGamesListUseCase,
    private val getGameByIdUseCase: GetGameByIdUseCase
) : ViewModel() {

    private val currentQuery = MutableLiveData("")
    var game: MutableLiveData<GameDetailsUI> = MutableLiveData(GameDetailsUI.Empty())
    var error: MutableLiveData<String> = MutableLiveData("")
    val loader: MutableLiveData<Boolean> = MutableLiveData(false)
    val games = currentQuery.switchMap { query ->
        getGamesListUseCase.getGames(search = query).cachedIn(viewModelScope)
    }

    fun searchGames(query: String) {
        currentQuery.postValue(query)
    }

    fun clearData() {
        game.postValue(GameDetailsUI.Empty())
    }

    fun getGameById(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            loader.postValue(true)
            when (val response = getGameByIdUseCase.getGameById(id)) {
                is ResponseResult.Success -> {
                    game.postValue(response.data)
                }
                is ResponseResult.Error -> {
                    error.postValue(response.error)
                }
            }
            loader.postValue(false)
        }
    }
}