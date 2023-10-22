package com.bm.currencyapplication.app.di

import com.bm.currencyapplication.network.ApiHelper
import com.bm.currencyapplication.network.ApiHelperImp
import com.bm.currencyapplication.network.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiHelperModule {

    @Provides
    @Singleton
    fun provideApiHelper(apiService: ApiService): ApiHelper {
        return ApiHelperImp(apiService)
    }

}