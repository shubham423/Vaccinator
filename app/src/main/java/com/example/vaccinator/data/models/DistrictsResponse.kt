package com.example.vaccinator.data.models


import com.google.gson.annotations.SerializedName

data class DistrictsResponse(
    @SerializedName("districts")
    val districts: List<District>,
    @SerializedName("ttl")
    val ttl: Int
) {
    data class District(
        @SerializedName("district_id")
        val districtId: Int,
        @SerializedName("district_name")
        val districtName: String,
        @SerializedName("district_name_l")
        val districtNameL: String,
        @SerializedName("state_id")
        val stateId: Int
    )
}