package com.bm.currencyapplication.network

import com.bm.currencyapplication.convertcurrency.domain.AvailableRatesModel
import com.bm.currencyapplication.convertcurrency.domain.HistoricalRateModel


import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiService {

    @GET("latest")
    suspend fun getAvailableRates(): AvailableRatesModel


    @POST("{date}")

    suspend fun getHistoricalData(
        @Path("date") date: String,
        @Query("base") base: String,
        @Query("symbols") symbols: String
    ): HistoricalRateModel


}