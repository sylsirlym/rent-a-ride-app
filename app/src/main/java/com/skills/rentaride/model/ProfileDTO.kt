package com.skills.rentaride.model

import com.google.gson.annotations.SerializedName

data class ProfileDTO(
    @SerializedName("pinStatus")
    val pinStatus: Int,
    @SerializedName("msisdn")
    val msisdn: String,
    @SerializedName("otherNames")
    val otherNames: String,
    @SerializedName("emailAddress")
    val emailAddress: String,
    @SerializedName("fname")
    val fname: String
)