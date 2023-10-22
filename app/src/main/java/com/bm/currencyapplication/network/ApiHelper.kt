package com.bm.currencyapplication.network

import com.bm.currencyapplication.convertcurrency.domain.AvailableRatesModel
import com.bm.currencyapplication.convertcurrency.domain.HistoricalRateModel
interface ApiHelper {

    suspend fun getAvailableRates(): AvailableRatesModel

    suspend fun getHistoricalRates(
        date: String,
        base: String,
        symbols: String
    ): HistoricalRateModel
}