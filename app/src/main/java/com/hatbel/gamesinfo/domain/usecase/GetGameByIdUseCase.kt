package com.hatbel.gamesinfo.domain.usecase

import com.hatbel.gamesinfo.data.models.GameDetails
import com.hatbel.gamesinfo.data.models.GameDetailsUI
import com.hatbel.gamesinfo.data.repositories.GamesRepository
import com.hatbel.gamesinfo.data.repositories.ResponseResult

interface GetGameByIdUseCase{
    suspend fun getGameById(id: Int): ResponseResult<GameDetailsUI.Base>

    class Base(
        private val repository: GamesRepository,
        private val mapper: GameDetails.Mapper<GameDetailsUI.Base>): GetGameByIdUseCase{
        override suspend fun getGameById(id: Int): ResponseResult<GameDetailsUI.Base> {
            return when (val response = repository.getGameById(id)) {
                is ResponseResult.Success -> ResponseResult.Success(response.data.map(mapper))
                is ResponseResult.Error -> ResponseResult.Error(response.error)
            }
        }

    }
}