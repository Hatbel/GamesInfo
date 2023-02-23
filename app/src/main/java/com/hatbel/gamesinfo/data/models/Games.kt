package com.hatbel.gamesinfo.data.models

import android.util.Log
import com.google.gson.annotations.SerializedName


data class GameList(
    val count: Int?,
    val next: String?,
    val previous: String?,
    val results: List<Game>?
)

interface Game{
    fun <T> map(mapper: Mapper<T>): T

    class Empty: Game{
        override fun <T> map(mapper: Mapper<T>): T = mapper.map(
            GameResponse(0,"","","",false,"",0.0,0,
        0,"",0, hashMapOf(),0,0,0,"",
                EsrbRating(0,"",""), mutableListOf()))
    }

    interface Mapper<T>{
        fun map(data : GameResponse?): T

        class Base: Mapper<GameUI>{
            override fun map(data: GameResponse?): GameUI {
                return GameUI(
                    data?.id ?: 0,
                    data?.slug ?: "",
                    data?.name ?: "",
                    data?.released ?: "",
                    data?.tba ?: false,
                    data?.backgroundImage ?: "",
                    data?.rating ?: 0.0,
                    data?.ratingsCount ?: 0,
                    data?.reviewsTextCount ?: ""
                )
            }

        }
    }
}

data class GamesListResponse(
    @SerializedName("count")
    val count: Int,
    @SerializedName("results")
    val results: List<GameResponse>
)

data class GameResponse(
    val id: Int?,
    val slug: String?,
    val name: String?,
    val released: String?,
    val tba: Boolean?,
    @SerializedName("background_image")
    val backgroundImage: String?,
    val rating: Double?,
    @SerializedName("ratings_top")
    val ratingTop: Int?,
    @SerializedName("ratings_count")
    val ratingsCount: Int?,
    @SerializedName("reviews_ext_count")
    val reviewsTextCount: String?,
    val added: Int?,
    @SerializedName("added_by_status")
    val addedByStatus: Map<String, Any>?,
    val metacritic: Int?,
    val playtime: Int?,
    @SerializedName("suggestions_count")
    val suggestionsCount: Int?,
    val updated: String?,
    @SerializedName("esrb_rating")
    val esrbRating: EsrbRating?,
    val platforms: List<Platform>?
) : Game {
    override fun <T> map(mapper: Game.Mapper<T>): T = mapper.map(this)
}

data class GameUI(
    val id: Int,
    val slug: String,
    val name: String,
    val released: String,
    val tba: Boolean,
    val backgroundImage: String,
    val rating: Double,
    @SerializedName("ratings_count")
    val ratingsCount: Int,
    @SerializedName("reviews_text_count")
    val reviewsTextCount: String)

data class EsrbRating(
    val id: Int?,
    val slug: String?,
    val name: String?
)

data class Platform(
    val platform: PlatformInfo?,
    val releasedAt: String?,
    val requirements: Requirements?
)

data class PlatformInfo(
    val id: Int?,
    val slug: String?,
    val name: String?
)

data class Requirements(
    val minimum: String?,
    val recommended: String?
)