package com.bm.currencyapplication.historicaldata.presentation

import com.bm.currencyapplication.utils.BaseIntent

sealed class HistoricalDataIntent : BaseIntent() {

    data class GetCurrencies(
        val currencyFrom: String,
        val currencyTo: String,
        val base: Double,
        val symbols: List<String>
    ) : HistoricalDataIntent()

}