package com.example.vaccinator.data.repository

import com.example.vaccinator.data.models.DistrictsResponse
import com.example.vaccinator.data.models.SlotResponse
import com.example.vaccinator.data.models.StatesResponse
import com.example.vaccinator.data.remote.ApiClient
import retrofit2.Response

class MainRepository {
    suspend fun getFeed(pincode:String, date:String): Response<SlotResponse> = ApiClient.api.getStatus(pincode,date)
    suspend fun getStates(): Response<StatesResponse> = ApiClient.api.getStates()
    suspend fun getDistricts(stateId: String): Response<DistrictsResponse> = ApiClient.api.getDistricts(stateId)
}