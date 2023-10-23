package ccom.bm.currencyapplication.historicaldata.presentation

import com.bm.currencyapplication.convertcurrency.domain.HistoricalRateModel

sealed class HistoricalDataState {

    object Initializing : HistoricalDataState()
    object Loading : HistoricalDataState()
    data class Error(val message: String) : HistoricalDataState()
    data class DailyHistoricalData(
        val result: List<HistoricalRateModel>,
        val rates: HashMap<String, Double>
    ) : HistoricalDataState()

}
