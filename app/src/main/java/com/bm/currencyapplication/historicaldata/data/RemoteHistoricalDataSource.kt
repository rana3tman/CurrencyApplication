package com.bm.currencyapplication.historicaldata.data

import com.bm.currencyapplication.convertcurrency.domain.HistoricalRateModel
import com.bm.currencyapplication.historicaldata.domain.HistoricalDataSource
import com.bm.currencyapplication.network.ApiHelper

import javax.inject.Inject

class RemoteHistoricalDataSource @Inject constructor(private val apiHelper: ApiHelper) :
    HistoricalDataSource {

    override suspend fun getHistoricalData(
        date: String,
        base: String,
        symbols: String
    ): HistoricalRateModel {
        return apiHelper.getHistoricalRates(date, base, symbols)
    }
}