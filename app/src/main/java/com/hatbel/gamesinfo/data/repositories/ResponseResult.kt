package com.hatbel.gamesinfo.data.repositories

sealed interface ResponseResult<D>{
    data class Success<D>(val data: D): ResponseResult<D>
    data class Error<D>(val error: String): ResponseResult<D>
}