package com.bm.currencyapplication.app.di


import com.bm.currencyapplication.convertcurrency.data.RemoteConvertCurrencyDataSource
import com.bm.currencyapplication.convertcurrency.domain.AvailableRatesRepository
import com.bm.currencyapplication.convertcurrency.domain.AvailableRatesRepositoryImp

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



}