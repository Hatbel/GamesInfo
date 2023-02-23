package com.hatbel.gamesinfo.data.models

import com.google.gson.annotations.SerializedName

interface GameDetails {

    fun <T> map(mapper: Mapper<T>): T

    class Empty : GameDetails {
        override fun <T> map(mapper: Mapper<T>): T =
            mapper.map(
                GameDetailsResponse(
                    0, "", "", "", "", 0,
                    "", false, "", "", "",
                    "", 0.0, 0, 0, 0, 0, 0, 0,
                    0, "", "", "", "", "", 0,
                    "", "", "", 0, 0, listOf(), "",
                    0, 0, 0, EsrbRating(0, "", ""), listOf()
                )
            )
    }


    data class GameDetailsResponse(
        val id: Int?,
        val slug: String?,
        val name: String?,
        @SerializedName("name_original")
        val nameOriginal: String?,
        val description: String?,
        val metacritic: Int?,
        val released: String?,
        val tba: Boolean?,
        val updated: String?,
        @SerializedName("background_image")
        val backgroundImage: String?,
        @SerializedName("background_image_additional")
        val backgroundImageAdditional: String?,
        val website: String?,
        val rating: Double?,
        @SerializedName("rating_top")
        val ratingTop: Int?,
        val added: Int?,
        val playtime: Int?,
        @SerializedName("screenshots_count")
        val screenshotsCount: Int?,
        @SerializedName("movies_count")
        val moviesCount: Int?,
        @SerializedName("creators_count")
        val creatorsCount: Int?,
        @SerializedName("achievements_count")
        val achievementsCount: Int?,
        @SerializedName("parent_achievements_count")
        val parentAchievementsCount: String?,
        @SerializedName("reddit_url")
        val redditUrl: String?,
        @SerializedName("reddit_name")
        val redditName: String?,
        @SerializedName("reddit_description")
        val redditDescription: String?,
        @SerializedName("reddit_logo")
        val redditLogo: String?,
        @SerializedName("reddit_count")
        val redditCount: Int?,
        @SerializedName("twitch_count")
        val twitchCount: String?,
        @SerializedName("youtube_count")
        val youtubeCount: String?,
        @SerializedName("reviews_text_count")
        val reviewsTextCount: String?,
        @SerializedName("ratings_count")
        val ratingsCount: Int?,
        @SerializedName("suggestions_count")
        val suggestionsCount: Int?,
        @SerializedName("alternative_names")
        val alternativeNames: List<String>?,
        @SerializedName("metacritic_url")
        val metacriticUrl: String?,
        @SerializedName("parents_count")
        val parentsCount: Int?,
        @SerializedName("additions_count")
        val additionsCount: Int?,
        @SerializedName("game_series_count")
        val gameSeriesCount: Int?,
        @SerializedName("esrb_rating")
        val esrbRating: EsrbRating?,
        val platforms: List<Platform>?
    ) : GameDetails {
        override fun <T> map(mapper: Mapper<T>): T = mapper.map(this)
    }

    interface Mapper<T> {
        fun map(data: GameDetailsResponse): T

        class Base : Mapper<GameDetailsUI> {
            override fun map(data: GameDetailsResponse): GameDetailsUI.Base =
                GameDetailsUI.Base(data.id ?: 0, data.name ?: "",
                    data.description ?: "", data.metacritic ?: 0,
                    data.released ?: "", data.updated ?: "",
                    data.backgroundImage ?: "", data.website ?: "",
                    data.rating ?: 0.0, data.playtime ?: 0,
                    data.twitchCount ?: "", data.youtubeCount ?: "",
                    data.platforms?.map { it.platform?.name ?: "" } ?: listOf())


        }
    }
}

interface GameDetailsUI {

    fun map():Base
    fun formPlatforms(): String
    fun formUpdate(): String

    data class Base(
        val id: Int,
        val name: String,
        val description: String,
        val metacritic: Int,
        val released: String,
        val updated: String,
        val backgroundImage: String,
        val website: String,
        val rating: Double,
        val playtime: Int?,
        val twitchCount: String,
        val youtubeCount: String,
        val platformNames: List<String>
    ):GameDetailsUI{
        override fun map() = this
        override fun formPlatforms(): String {
            var platforms = ""
            platformNames.forEach{
                platforms += "\n$it"
            }
            return platforms
        }

        override fun formUpdate(): String = updated.split("T").first()

    }

    class Empty: GameDetailsUI {
        override fun map() = Base(0,"","",0,
            "","","","",0.0,
            0,"","", listOf()
        )

        override fun formPlatforms(): String = ""
        override fun formUpdate(): String = ""
    }
}