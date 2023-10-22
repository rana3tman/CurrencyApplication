package com.bm.currencyapplication.convertcurrency.domain

import javax.inject.Inject

class AvailableRatesRepositoryImp @Inject constructor(private val convertCurrencyDataSource: ConvertCurrencyDataSource) :
    AvailableRatesRepository {
    override suspend fun getAllAvailableRates() = convertCurrencyDataSource.getAllAvailableRates()
    override suspend fun getAllCurrencyRate(
        date: String,
        base: String,
        symbols: String
    ) = convertCurrencyDataSource.getAllCurrencyRate(date, base, symbols)


}