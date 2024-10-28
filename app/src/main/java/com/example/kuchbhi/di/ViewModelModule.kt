package com.example.kuchbhi.di

import com.example.kuchbhi.MainViewModel
import com.example.kuchbhi.database.dao.SmsDao
import com.example.kuchbhi.viewModels.HomeViewModel
import com.example.kuchbhi.viewModels.SmsViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ViewModelModule {

    @Provides
    @Singleton
    fun provideSmsViewModel(smsDao: SmsDao): SmsViewModel {
        return SmsViewModel(smsDao)
    }

    @Provides
    @Singleton
    fun provideMainViewModel(): MainViewModel {
        return MainViewModel()
    }

}