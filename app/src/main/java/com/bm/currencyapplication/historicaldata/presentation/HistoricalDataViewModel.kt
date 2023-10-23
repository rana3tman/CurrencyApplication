package com.bm.currencyapplication.historicaldata.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.bm.currencyapplication.convertcurrency.domain.HistoricalRateModel
import com.bm.currencyapplication.historicaldata.domain.HistoricalDataUsecase
import com.bm.currencyapplication.network.ErrorHandler

import com.example.currencyconverterapp.utils.BaseViewModel
import com.example.currencyconverterapp.utils.Calculator
import com.example.currencyconverterapp.utils.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

import javax.inject.Inject

@HiltViewModel
class HistoricalDataViewModel @Inject constructor(private var historicalDataUsecase: HistoricalDataUsecase) :
    BaseViewModel<HistoricalDataIntent>() {
    private val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.US)

    private var firstDay: String
    private var secondDay: String
    private var thirdDay: String


    init {
        val cal = Calendar.getInstance()
        cal.add(Calendar.DATE, -1)
        firstDay = formatter.format(cal.time)
        cal.add(Calendar.DATE, -1)
        secondDay = formatter.format(cal.time)
        cal.add(Calendar.DATE, -1)
        thirdDay = formatter.format(cal.time)


    }

    private var _items = MutableLiveData<HistoricalDataState>(HistoricalDataState.Initializing)
    val items: LiveData<HistoricalDataState>
        get() = _items

    override fun onTriggerEvent(eventType: HistoricalDataIntent) {
        when (eventType) {

            is HistoricalDataIntent.GetCurrencies -> {
                getOtherCurrencies(
                    eventType.currencyFrom,
                    eventType.currencyFrom,
                    eventType.base,
                    eventType.symbols
                )
            }

        }
    }

    private fun getOtherCurrencies(
        currencyFrom: String,
        currencyTo: String,
        base: Double,
        symbols: List<String>
    ) {
        val arraySymbols = ArrayList(symbols)
        for (symbol in arraySymbols) {
            if (symbol.equals(currencyFrom))
                arraySymbols.remove(symbol)
        }
        if (arraySymbols.size > 10) arraySymbols.removeLast()

        val allRates = viewModelScope.async(Dispatchers.IO) {
            historicalDataUsecase.invoke(firstDay, currencyFrom, arraySymbols.joinToString())
        }

        val firstDayData = viewModelScope.async(Dispatchers.IO) {
            historicalDataUsecase.invoke(firstDay, currencyFrom, currencyTo)
        }
        val secDayData = viewModelScope.async(Dispatchers.IO) {
            historicalDataUsecase.invoke(secondDay, currencyFrom, currencyTo)
        }
        val thirdDayData = viewModelScope.async(Dispatchers.IO) {
            historicalDataUsecase.invoke(thirdDay, currencyFrom, currencyTo)
        }


        viewModelScope.launch {
            try {

                var rates = HashMap<String, Double>()

                allRates.await().collect { dataState ->
                    checkState(dataState)?.let { result -> rates = result.rates }

                }
                val result = HashMap<String, Double>()
                rates.map { entry ->
                    result.put(entry.key, Calculator.calculateConversionMultiply(base, entry.value))
                }

                val allData = ArrayList<HistoricalRateModel>()

                awaitAll(firstDayData, secDayData, thirdDayData).map { collection ->
                    collection.collect { dataState ->
                        checkState(dataState)?.let { result -> allData.add(result) }
                    }
                }

                if (allData.size >= 3) {
                    postState(HistoricalDataState.DailyHistoricalData(allData, result))
                }

            } catch (exception: Exception) {
                postState(HistoricalDataState.Error(ErrorHandler.handleFailureRequest(exception)))
            }
        }


    }


    private fun checkState(state: DataState<HistoricalRateModel>): HistoricalRateModel? {
        return when (state) {
            is DataState.Initializing -> {
                postState(HistoricalDataState.Loading)
                null
            }

            is DataState.Success -> {
                state.data
            }

            is DataState.Error -> {
                postState(HistoricalDataState.Error(state.message))
                null
            }
        }
    }

    private fun postState(state: HistoricalDataState) {
        _items.postValue(state)
    }


}