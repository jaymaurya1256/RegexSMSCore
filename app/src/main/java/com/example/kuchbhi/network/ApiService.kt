package com.example.kuchbhi.network

import com.example.kuchbhi.network.models.Sms
import com.example.kuchbhi.network.models.SmsResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @POST("{endpoint}")
    suspend fun postData(
        @Path("endpoint") dynamicPart: String,
        @Body requestData: Sms
    ): Response<SmsResponse>
}
