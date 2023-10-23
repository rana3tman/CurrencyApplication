package com.bm.currencyapplication.app.di


import com.bm.currencyapplication.convertcurrency.data.RemoteConvertCurrencyDataSource
import com.bm.currencyapplication.convertcurrency.domain.AvailableRatesRepository
import com.bm.currencyapplication.convertcurrency.domain.AvailableRatesRepositoryImp
import com.bm.currencyapplication.historicaldata.data.RemoteHistoricalDataSource
import com.bm.currencyapplication.historicaldata.domain.HistoricalDataRepository
import com.bm.currencyapplication.historicaldata.domain.HistoricalDataRepositoryImp

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    fun provideRepository(dataSource: RemoteConvertCurrencyDataSource): AvailableRatesRepository {
        return AvailableRatesRepositoryImp(dataSource)
    }

    @Provides
    fun provideDetailsRepository(dataSource: RemoteHistoricalDataSource): HistoricalDataRepository {
        return HistoricalDataRepositoryImp(dataSource)
    }

}