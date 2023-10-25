package com.bm.currencyapplication.convertcurrency.presentation


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.bm.currencyapplication.convertcurrency.domain.AvailableRatesUsecase
import com.bm.currencyapplication.convertcurrency.domain.ConvertCurrencyUsecase
import com.bm.currencyapplication.network.ErrorHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.bm.currencyapplication.utils.BaseViewModel
import com.bm.currencyapplication.utils.Calculator
import com.bm.currencyapplication.utils.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class ConvertCurrencyViewModel @Inject constructor(
    private var convertCurrencyUsecase: ConvertCurrencyUsecase,
    private var availableRatesUsecase: AvailableRatesUsecase
) :
    BaseViewModel<ConvertCurrencyIntent>() {
    private var amountValue = "1"
    private var resultValue = "1"
    var amountCurrentValue = MutableLiveData("1")
    var resultCurrentValue = MutableLiveData("")

    var currencyFrom = MutableLiveData(0)
    var currencyTo = MutableLiveData(0)
    var currentRate: Double = 0.0
    private lateinit var rateValues: List<Double>
    private var rateKeys = listOf<String>()
    var fromKeys = listOf<String>()


    var toKeys = listOf<String>()


    private var _items = MutableLiveData<ConvertCurrencyState>(ConvertCurrencyState.Initializing)
    val items: LiveData<ConvertCurrencyState>
        get() = _items
    private val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.US)

    private var today: String

    init {
        val cal = Calendar.getInstance()
        cal.add(Calendar.DATE, -1)
        today = formatter.format(cal.time)
    }

    override fun onTriggerEvent(eventType: ConvertCurrencyIntent) {
        when (eventType) {
            is ConvertCurrencyIntent.GetAvailableRates -> getAvailableRates()
            is ConvertCurrencyIntent.SwapRates -> {
                currencyFrom.value = eventType.currencyTo
                currencyTo.value = eventType.currencyFrom
                swapAmounts()
            }

        }
    }

    private fun postState(state: ConvertCurrencyState) {
        _items.postValue(state)
    }

    private fun getAvailableRates() {
        viewModelScope.launch(Dispatchers.IO) {
            try {

                availableRatesUsecase.invoke().collect { dataState ->
                    when (dataState) {
                        is DataState.Initializing -> {
                            postState(ConvertCurrencyState.Loading)
                        }

                        is DataState.Success -> {
                            rateKeys = dataState.data.rates.keys.toList()
                            fromKeys = listOf("From").plus(rateKeys)
                            toKeys = listOf("To").plus(rateKeys)

                            rateValues = dataState.data.rates.values.toList()
                            postState(ConvertCurrencyState.AllRatesAvailable(dataState.data))

                        }

                        is DataState.Error -> {
                            postState(ConvertCurrencyState.Error(dataState.message))
                        }

                    }
                }


            } catch (exception: Exception) {
                postState(ConvertCurrencyState.Error(ErrorHandler.handleFailureRequest(exception)))
            }

        }
    }

    private fun swapAmounts() {

        amountValue = resultCurrentValue.value ?: ""
        amountCurrentValue.value = amountValue
        currencyConverter(amountValue)

    }

    fun onAmountTextChanged(amount: CharSequence) {

        if (amountValue != amount.toString()) {
            amountValue = amount.toString()
            currencyConverter(amountValue)

        }

    }


    fun onResultTextChanged(result: CharSequence) {
        if (resultValue != result.toString()) {
            resultValue = result.toString()
            if (currentRate != 0.0) {
                amountValue =
                    Calculator.calculateConversionDivision(resultValue.toDouble(), currentRate)
                        .toString() //currencyConverter(resultValue)
                postState(ConvertCurrencyState.ResultConverted(amountValue))
            }
        }

    }


    private fun currencyConverter(amount: String) {
        if (currencyFrom.value == 0) {
            postState(ConvertCurrencyState.Error("You must select currency from first"))
        } else if (currencyTo.value == 0) {
            postState(ConvertCurrencyState.Error("You must select currency to first"))
        } else {
            if (amount.isNotEmpty()) {


                val rate = viewModelScope.async(Dispatchers.IO) {

                    convertCurrencyUsecase.invoke(
                        today,
                        rateKeys[currencyFrom.value!! - 1],
                        rateKeys[currencyTo.value!! - 1]
                    )
                }

                viewModelScope.launch {
                    rate.await().collect { dataState ->
                        when (dataState) {
                            is DataState.Initializing -> {}
                            is DataState.Success -> {
                                currentRate =
                                    dataState.data.rates[rateKeys[currencyTo.value!! - 1]]!!
                                resultValue = Calculator.calculateConversionMultiply(
                                    amount.toDouble(),
                                    currentRate
                                ).toString()
                                postState(ConvertCurrencyState.AmountConverted(resultValue))
                            }

                            is DataState.Error -> {
                                resultValue = ""
                                resultCurrentValue.value = resultValue

                                postState(ConvertCurrencyState.Error(dataState.message))
                            }

                        }
                    }

                }
            } else {
                resultValue = ""

                postState(ConvertCurrencyState.AmountConverted(resultValue))
            }

        }
    }
}