package com.example.kuchbhi.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kuchbhi.database.dao.SmsDao
import com.example.kuchbhi.database.entities.Sms
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SmsViewModel @Inject constructor(private val smsDao: SmsDao) : ViewModel() {
    fun insertSms(sms: Sms) {
        viewModelScope.launch {
            smsDao.insert(sms)
        }
    }

    fun updateSms(sms: Sms) {
        viewModelScope.launch {
            smsDao.update(sms)
        }
    }
}
