package com.bm.currencyapplication.convertcurrency.presentation

import com.bm.currencyapplication.convertcurrency.domain.AvailableRatesModel


sealed class ConvertCurrencyState {

    object Initializing : ConvertCurrencyState()
    object Loading : ConvertCurrencyState()
    data class Error(val message: String) : ConvertCurrencyState()
    data class AmountConverted(val result: String) : ConvertCurrencyState()
    data class ResultConverted(val amount: String) : ConvertCurrencyState()
    data class AmountsUpdated(val newAmount: String, val newResult: String) : ConvertCurrencyState()
    data class AllRatesAvailable(val data: AvailableRatesModel) : ConvertCurrencyState()


}
