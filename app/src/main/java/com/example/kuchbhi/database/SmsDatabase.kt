package com.example.kuchbhi.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import com.example.kuchbhi.database.dao.SmsDao
import com.example.kuchbhi.database.entities.Sms

@Database(entities = [Sms::class], version = 1, exportSchema = false)
abstract class SmsDatabase : RoomDatabase() {
    abstract fun smsDao(): SmsDao

}
