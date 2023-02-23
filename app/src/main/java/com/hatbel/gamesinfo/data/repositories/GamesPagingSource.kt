package com.hatbel.gamesinfo.data.repositories

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.hatbel.gamesinfo.data.dataSource.ApiService
import com.hatbel.gamesinfo.data.models.GameResponse

class GamesPagingSource(
    private val service: ApiService,
    private val search: String
) : PagingSource<Int, GameResponse>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GameResponse> {
        val pageIndex = params.key ?: 1
        return try {
            val response = service.getGames(page = pageIndex, search = search)
            val responseData = mutableListOf<GameResponse>()
            responseData.addAll(response.body()?.results.orEmpty())
            val prevKey = if (pageIndex == 1) null else pageIndex - 1
            LoadResult.Page(data = responseData, prevKey = prevKey, nextKey = pageIndex + 1)
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, GameResponse>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}