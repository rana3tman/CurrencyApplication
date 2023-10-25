package com.bm.currencyapplication.historicaldata.domain

import com.bm.currencyapplication.utils.DataState
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class HistoricalDataUsecase @Inject constructor(private var historicalDataRepository: HistoricalDataRepository) {
    suspend fun invoke(date: String, base: String, symbols: String) = flow {
        emit(DataState.Initializing)
        val result = historicalDataRepository.getHistoricalData(date, base, symbols)
        emit(
            if (result.success)
                DataState.Success(result)
            else DataState.Error(result.error.info)
        )

    }
}