package com.bm.currencyapplication.convertcurrency.domain


interface AvailableRatesRepository {
    suspend fun getAllAvailableRates(): AvailableRatesModel

    suspend fun getAllCurrencyRate(
        date: String,
        base: String,
        symbols: String
    ): HistoricalRateModel
}