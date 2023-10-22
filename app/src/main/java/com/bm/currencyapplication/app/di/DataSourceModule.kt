package com.bm.currencyapplication.app.di

import com.bm.currencyapplication.convertcurrency.data.RemoteConvertCurrencyDataSource
import com.bm.currencyapplication.network.ApiHelperImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DataSourceModule {

    @Provides

    fun provideDataSource(apiHelper: ApiHelperImp): RemoteConvertCurrencyDataSource {
        return RemoteConvertCurrencyDataSource(apiHelper)
    }



}