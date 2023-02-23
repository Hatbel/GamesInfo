package com.hatbel.gamesinfo.data.repositories

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.hatbel.gamesinfo.data.dataSource.ApiService
import com.hatbel.gamesinfo.data.models.GameDetails
import com.hatbel.gamesinfo.data.models.GameDetailsUI
import com.hatbel.gamesinfo.data.models.GameResponse
import com.hatbel.gamesinfo.data.models.GamesListResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response


interface GamesRepository {

    fun getGames(
        pageSize: Int = 20,
        search: String = ""
    ): LiveData<PagingData<GameResponse>>

    suspend fun getGameById(id: Int): ResponseResult<GameDetails.GameDetailsResponse>

    class Base(private val apiService: ApiService) : GamesRepository {
        override fun getGames(
            pageSize: Int,
            search: String
        ): LiveData<PagingData<GameResponse>> =
            Pager(PagingConfig(pageSize = 20)) {
                GamesPagingSource(apiService, search)
            }.liveData

        override suspend fun getGameById(id: Int): ResponseResult<GameDetails.GameDetailsResponse> {
            return try{
                val response = apiService.getGameById(id).body()
                response?.let{ResponseResult.Success(it)} ?: ResponseResult.Error("object is null")
            } catch (e: Exception){
                ResponseResult.Error(e.message.toString())
            }
        }
    }
}