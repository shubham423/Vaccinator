package com.example.vaccinator.data.models


import com.google.gson.annotations.SerializedName

data class StatesResponse(
    @SerializedName("states")
    val states: List<State>,
    @SerializedName("ttl")
    val ttl: Int
) {
    data class State(
        @SerializedName("state_id")
        val stateId: Int,
        @SerializedName("state_name")
        val stateName: String,
        @SerializedName("state_name_l")
        val stateNameL: String
    )
}