package com.bm.currencyapplication.app.di


import com.bm.currencyapplication.convertcurrency.domain.AvailableRatesRepository
import com.bm.currencyapplication.convertcurrency.domain.AvailableRatesUsecase
import com.bm.currencyapplication.convertcurrency.domain.ConvertCurrencyUsecase
import com.bm.currencyapplication.historicaldata.domain.HistoricalDataRepository
import com.bm.currencyapplication.historicaldata.domain.HistoricalDataUsecase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Provides

    fun provideConvertUseCase(repository: AvailableRatesRepository): ConvertCurrencyUsecase {
        return ConvertCurrencyUsecase(repository)
    }

    @Provides

    fun provideAvailableRatesUseCase(repository: AvailableRatesRepository): AvailableRatesUsecase {
        return AvailableRatesUsecase(repository)
    }

    @Provides

    fun provideDetailsUseCase(repository: HistoricalDataRepository): HistoricalDataUsecase {
        return HistoricalDataUsecase(repository)
    }

}