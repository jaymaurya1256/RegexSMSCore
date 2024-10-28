package com.example.kuchbhi.di

import android.content.Context
import androidx.room.Room
import com.example.kuchbhi.database.SmsDatabase
import com.example.kuchbhi.database.dao.SmsDao
import com.example.kuchbhi.viewModels.SmsViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideSmsDatabase(@ApplicationContext context: Context): SmsDatabase {
        return Room.databaseBuilder(
            context,
            SmsDatabase::class.java,
            "sms_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideDao(database: SmsDatabase): SmsDao {
        return database.smsDao()
    }

}
