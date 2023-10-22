package com.bm.currencyapplication.network

import com.bm.currencyapplication.convertcurrency.domain.AvailableRatesModel
import com.bm.currencyapplication.convertcurrency.domain.HistoricalRateModel

import javax.inject.Inject

class ApiHelperImp @Inject constructor(private val apiService: ApiService) : ApiHelper {
    override suspend fun getAvailableRates(): AvailableRatesModel {
        return apiService.getAvailableRates()
    }

    override suspend fun getHistoricalRates(
        date: String,
        base: String,
        symbols: String
    ): HistoricalRateModel {
        return apiService.getHistoricalData(date, base, symbols)
    }
}