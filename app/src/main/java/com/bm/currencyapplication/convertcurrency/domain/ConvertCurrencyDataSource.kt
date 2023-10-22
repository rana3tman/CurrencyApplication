package com.bm.currencyapplication.convertcurrency.domain


interface ConvertCurrencyDataSource {
    suspend fun getAllAvailableRates(): AvailableRatesModel
    suspend fun getAllCurrencyRate(
        date: String,
        base: String,
        symbols: String
    ): HistoricalRateModel

}