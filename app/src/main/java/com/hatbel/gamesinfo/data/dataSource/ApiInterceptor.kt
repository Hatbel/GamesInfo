package com.hatbel.gamesinfo.data.dataSource

import com.hatbel.gamesinfo.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

private const val API_KEY = "2d3f2e29aad0439d85127c45ed96679a"

class ApiInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val url = request.url
            .newBuilder()
            .addQueryParameter("key", API_KEY)
            .build()
        return chain.proceed(request.newBuilder().url(url).build())
    }
}