package com.bm.currencyapplication.convertcurrency.presentation

import com.example.currencyconverterapp.utils.BaseIntent

sealed class ConvertCurrencyIntent : BaseIntent() {

    object GetAvailableRates : ConvertCurrencyIntent()
    data class SwapRates(val currencyFrom: Int, val currencyTo: Int) : ConvertCurrencyIntent()
}