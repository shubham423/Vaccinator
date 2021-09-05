package com.example.vaccinator.data.repository

import com.example.vaccinator.data.models.SlotResponse
import com.example.vaccinator.data.remote.ApiClient
import retrofit2.Response

class MainRepository {
    suspend fun getFeed(pincode:String, date:String): Response<SlotResponse> = ApiClient.api.getStatus(pincode,date)
}