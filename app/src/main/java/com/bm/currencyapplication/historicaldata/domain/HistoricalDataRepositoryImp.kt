package com.bm.currencyapplication.historicaldata.domain

import javax.inject.Inject


class HistoricalDataRepositoryImp @Inject constructor(private val convertCurrencyDataSource: HistoricalDataSource) :
    HistoricalDataRepository {
    override suspend fun getHistoricalData(date: String, base: String, symbols: String) =
        convertCurrencyDataSource.getHistoricalData(date, base, symbols)


}