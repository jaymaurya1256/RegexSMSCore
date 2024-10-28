package com.example.kuchbhi.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.kuchbhi.database.entities.Sms
import kotlinx.coroutines.flow.Flow

@Dao
interface SmsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(sms: Sms)

    @Update
    suspend fun update(sms: Sms)

    @Query("SELECT * FROM sms_table ORDER BY id DESC")
    fun getAllSms(): Flow<List<Sms>>
}
