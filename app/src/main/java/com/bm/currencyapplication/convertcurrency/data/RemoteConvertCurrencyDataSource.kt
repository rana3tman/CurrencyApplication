package com.bm.currencyapplication.convertcurrency.data

import com.bm.currencyapplication.convertcurrency.domain.AvailableRatesModel
import com.bm.currencyapplication.convertcurrency.domain.ConvertCurrencyDataSource
import com.bm.currencyapplication.convertcurrency.domain.HistoricalRateModel
import com.bm.currencyapplication.network.ApiHelper
import javax.inject.Inject

class RemoteConvertCurrencyDataSource @Inject constructor(private val apiHelper: ApiHelper) :
    ConvertCurrencyDataSource {

    override suspend fun getAllAvailableRates(): AvailableRatesModel {
        return apiHelper.getAvailableRates()

    }

    override suspend fun getAllCurrencyRate(
        date: String,
        base: String,
        symbols: String
    ): HistoricalRateModel {
        return apiHelper.getHistoricalRates(date, base, symbols)
    }
}