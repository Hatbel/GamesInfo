package com.hatbel.gamesinfo.domain.di

import com.hatbel.gamesinfo.data.dataSource.ApiInterceptor
import com.hatbel.gamesinfo.data.dataSource.ApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val SERVER_BASE_URL = "https://api.rawg.io/api/"

val networkModule = module {
    single { provideDefaultOkHttpClient() }
    single { provideRetrofit(get()) }
    single { provideApiService(get()) }
}

fun provideRetrofit(client: OkHttpClient): Retrofit =
    Retrofit.Builder()
        .baseUrl(SERVER_BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

fun provideDefaultOkHttpClient(): OkHttpClient {
    val logging = HttpLoggingInterceptor()
    logging.level = HttpLoggingInterceptor.Level.BODY

    val httpClient = OkHttpClient.Builder().addInterceptor(logging)
        .addInterceptor(ApiInterceptor())
        .addInterceptor{
        chain ->  val request = chain.request().newBuilder()
        .get()
        .addHeader("X-RapidAPI-Host", "rawg-video-games-database.p.rapidapi.com")
        .addHeader("X-RapidAPI-Key", "2d3f2e29aad0439d85127c45ed96679a")
        .build()
        chain.proceed(request)
    }
    return httpClient.build()
}

fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)