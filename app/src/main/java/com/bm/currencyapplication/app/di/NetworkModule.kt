package com.bm.currencyapplication.app.di

import com.bm.currencyapplication.network.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val baseURL = "http://data.fixer.io/api/"
    private const val accessKey = "0efab69c97daf60af1f27ca34e3615e0"

    private val logger =
        HttpLoggingInterceptor().apply { setLevel(HttpLoggingInterceptor.Level.BODY) }

    private val api_interceptor = Interceptor { chain ->
        val original = chain.request()
        val requestBuilder = original.newBuilder().url(
            chain.request().url.newBuilder().addQueryParameter(
                "access_key",
                accessKey
            ).build()
        )

        val request = requestBuilder.build()
        chain.proceed(request)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient() =
        OkHttpClient().newBuilder()

            .addInterceptor(logger)  //optional
            .addInterceptor(api_interceptor)
            .connectTimeout(2, TimeUnit.MINUTES)
            .readTimeout(2, TimeUnit.MINUTES)
            .writeTimeout(2, TimeUnit.MINUTES)

            .build()

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(baseURL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)

}