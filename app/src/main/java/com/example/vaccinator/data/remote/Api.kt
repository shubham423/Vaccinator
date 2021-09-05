package com.example.vaccinator.data.remote

import com.example.vaccinator.data.models.SlotResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET("/api/v2/appointment/sessions/public/findByPin")
    suspend fun getStatus(
        @Query("pincode") author: String? = null,
        @Query("date") tag: String? = null,
    ): Response<SlotResponse>
}