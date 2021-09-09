package com.example.vaccinator.data.remote

import com.example.vaccinator.data.models.DistrictsResponse
import com.example.vaccinator.data.models.SlotResponse
import com.example.vaccinator.data.models.StatesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {

    @GET("/api/v2/appointment/sessions/public/findByPin")
    suspend fun getStatus(
        @Query("pincode") author: String? = null,
        @Query("date") tag: String? = null,
    ): Response<SlotResponse>

    @GET("/api/v2/admin/location/states")
    suspend fun getStates(
    ): Response<StatesResponse>

    @GET("/api/v2/admin/location/districts/{state_id}")
    suspend fun getDistricts(
        @Path("state_id") state_id: String
    ): Response<DistrictsResponse>
}