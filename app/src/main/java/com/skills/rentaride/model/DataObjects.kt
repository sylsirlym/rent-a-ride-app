package com.skills.rentaride.model

import com.google.gson.annotations.SerializedName

data class ResponseDTO (
    @SerializedName("statusCode")
    val statusCode: Int,
    @SerializedName("statusMessage")
    val statusMessage: String,
    @SerializedName("data")
    val data: MutableList<Any>?
)

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

data class RentTransactionDTO(
    @SerializedName("lendTransactionStatusID")
    val lendTransactionStatusID: Int,
    @SerializedName("lendItemTypeName")
    val lendItemTypeName: String,
    @SerializedName("serialNumber")
    val serialNumber: String,
    @SerializedName("lendItemCost")
    val lendItemCost: Float,
    @SerializedName("lendTransactionStatus")
    val lendTransactionStatus: String
)