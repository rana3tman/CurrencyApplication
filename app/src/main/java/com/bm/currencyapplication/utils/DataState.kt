package com.example.currencyconverterapp.utils

sealed class DataState<out T> {

    object Initializing : DataState<Nothing>()
    data class Error(val message: String) : DataState<Nothing>()
    data class Success<out T>(val data: T) : DataState<T>()
}
