package com.bm.currencyapplication.convertcurrency.domain

import com.bm.currencyapplication.utils.ErrorModel

data class HistoricalRateModel(
    var success: Boolean,
    var timestamp: Long,
    var historical: Boolean,
    var base: String,
    var date: String,
    var rates: HashMap<String, Double>,
    var error: ErrorModel
)
