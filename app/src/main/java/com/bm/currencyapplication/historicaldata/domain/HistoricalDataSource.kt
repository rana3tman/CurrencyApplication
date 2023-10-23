package com.bm.currencyapplication.historicaldata.domain

import com.bm.currencyapplication.convertcurrency.domain.HistoricalRateModel


interface HistoricalDataSource {
    suspend fun getHistoricalData(date: String, base: String, symbols: String): HistoricalRateModel

}