package com.hatbel.gamesinfo.data.dataSource

import com.hatbel.gamesinfo.data.models.GameDetails
import com.hatbel.gamesinfo.data.models.GamesListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("games")
    suspend fun getGames(
        @Query("page") page: Int,
        @Query("page_size") pageSize: Int = 20,
        @Query("search") search: String = ""
    ): Response<GamesListResponse>

    @GET("games/{id}")
    suspend fun getGameById(
        @Path("id") id: Int
    ): Response<GameDetails.GameDetailsResponse>
}