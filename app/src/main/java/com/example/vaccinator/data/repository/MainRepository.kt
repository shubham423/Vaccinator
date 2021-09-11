package com.example.vaccinator.data.repository

import com.example.vaccinator.data.models.DistrictsResponse
import com.example.vaccinator.data.models.SlotResponse
import com.example.vaccinator.data.models.StatesResponse
import com.example.vaccinator.data.remote.Api
import retrofit2.Response
import javax.inject.Inject

class MainRepository @Inject constructor(private val api: Api) {
    suspend fun getFeed(pincode: String, date: String): Response<SlotResponse> =
        api.getStatus(pincode, date)

    suspend fun getStates(): Response<StatesResponse> = api.getStates()

    suspend fun getDistricts(stateId: String): Response<DistrictsResponse> =
        api.getDistricts(stateId)
}