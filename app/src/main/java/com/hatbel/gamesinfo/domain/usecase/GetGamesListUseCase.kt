package com.hatbel.gamesinfo.domain.usecase

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import androidx.paging.PagingData
import androidx.paging.map
import com.hatbel.gamesinfo.data.models.Game
import com.hatbel.gamesinfo.data.models.GameResponse
import com.hatbel.gamesinfo.data.models.GameUI
import com.hatbel.gamesinfo.data.models.GamesListResponse
import com.hatbel.gamesinfo.data.repositories.GamesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import retrofit2.Response

interface GetGamesListUseCase {

    fun getGames(pageSize: Int = 20, search: String = ""): LiveData<PagingData<GameUI>>

    class Base(private val gamesRepository: GamesRepository,
               private val mapper: Game.Mapper<GameUI>):GetGamesListUseCase{
        override fun getGames(pageSize: Int, search: String): LiveData<PagingData<GameUI>> =
            gamesRepository.getGames(pageSize,search).map { pagingData: PagingData<GameResponse> ->
                pagingData.map { games: GameResponse ->
                    games.map(mapper)
                }
            }
    }
}