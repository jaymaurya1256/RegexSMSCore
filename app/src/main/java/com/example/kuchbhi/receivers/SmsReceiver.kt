package com.example.kuchbhi.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.telephony.SmsMessage
import android.util.Log
import com.example.kuchbhi.database.entities.Sms
import com.example.kuchbhi.network.ApiService
import com.example.kuchbhi.utils.BASE_URL
import com.example.kuchbhi.utils.PreferenceHelper
import com.example.kuchbhi.utils.containsInList
import com.example.kuchbhi.viewModels.SmsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

private const val TAG = "SmsReceiver"

@AndroidEntryPoint
class SmsBroadcastReceiver : BroadcastReceiver() {
    @Inject
    lateinit var smsViewModel: SmsViewModel

    @Inject
    lateinit var apiService: ApiService

    override fun onReceive(context: Context, intent: Intent) {
        val clients = PreferenceHelper(context).getStringList("clients")
        val regex = PreferenceHelper(context).getString("regex")
        val baseUrl = PreferenceHelper(context).getString("baseUrl")
        Log.d(TAG, "onReceive: regex $regex baseUrl $baseUrl")
        val mode = PreferenceHelper(context).getString("mode")
        val regexToMatch = regex?.let { Regex(Regex.escape(regex), RegexOption.IGNORE_CASE) }
        if (intent.action == "android.provider.Telephony.SMS_RECEIVED"
            || intent.action == "android.provider.Telephony.SMS_DELIVER"
        ) {
            val bundle: Bundle? = intent.extras
            if (bundle != null) {
                val pdus = bundle.get("pdus") as Array<*>
                for (pdu in pdus) {
                    val smsMessage = SmsMessage.createFromPdu(pdu as ByteArray)
                    val sender = smsMessage.displayOriginatingAddress
                    val messageBody = smsMessage.messageBody
                    Log.d(TAG, "onReceive: sender $sender")
                    Log.d(TAG, "onReceive: list of clients $clients")
                    if (regexToMatch != null) {
                        Log.d(TAG, "onReceive: regex to match $regexToMatch")
                        if (mode == "or") {
                            if (regexToMatch.containsMatchIn(messageBody) ||
                                (clients.isNotEmpty() && containsInList(sender, clients))
                            ) {
                                Log.d(TAG, "onReceive: storing in database in or mode")
                                saveSmsToDatabase(sender, messageBody, context)
                            } else {
                                Log.d(TAG, "onReceive: expression did not matched")
                            }
                        } else {
                            if (regexToMatch.containsMatchIn(messageBody) &&
                                (clients.isNotEmpty() && containsInList(sender, clients))
                            ) {
                                Log.d(TAG, "onReceive: storing in database in and mode")
                                saveSmsToDatabase(sender, messageBody, context)
                            } else {
                                Log.d(TAG, "onReceive: expression did not matched")
                            }
                        }
                    } else {
                        saveSmsToDatabase(sender, messageBody, context)
                    }
                }
            }
        }
    }

    private fun saveSmsToDatabase(sender: String, message: String, context: Context) {
        val sms = Sms(name = sender, content = message)
        CoroutineScope(Dispatchers.IO).launch {
            smsViewModel.insertSms(sms)
            val smsBody = com.example.kuchbhi.network.models.Sms(name = sender, message = message)
            sendToServer(smsBody, sms, context)
        }
    }

    private fun sendToServer(
        sms: com.example.kuchbhi.network.models.Sms,
        smsDb: Sms,
        context: Context
    ) {
        val baseUrl = PreferenceHelper(context).getString("baseUrl")
        CoroutineScope(Dispatchers.IO).launch {
            try {
                if (!baseUrl.isNullOrBlank()) {
                    Log.d(TAG, "sendToServer: baseUrl${baseUrl.substringBeforeLast("/").let { "$it/" }}")
                    Log.d(TAG, "sendToServer: endpoint${baseUrl.substringAfterLast("/")}")
                    val service = baseUrl.let { string ->
                        Retrofit.Builder()
                            .baseUrl(string.substringBeforeLast("/").let { "$it/" })
                            .addConverterFactory(GsonConverterFactory.create())
                            .build()
                            .create(ApiService::class.java)
                    }
                    Log.d(TAG, "sendToServer: sendToServer1")
                    val response = service.postData(baseUrl.substringAfterLast("/"), sms)
                    Log.d(TAG, "sendToServer1: $response")
                    if (response.isSuccessful) {
                        Log.d(TAG, "sendToServer1: Response returned successfully")
                        smsDb.sendToServer = true
                        smsViewModel.updateSms(smsDb)
                    }
                }
            } catch (e: Exception) {
                Log.d(TAG, "sendToServer: $e")
            }
        }
    }
}
