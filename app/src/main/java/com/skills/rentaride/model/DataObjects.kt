package com.skills.rentaride.model

import com.google.gson.annotations.SerializedName
import io.reactivex.Single

data class ResponseDTO(
    @SerializedName("statusCode")
    val statusCode: Int,
    @SerializedName("statusMessage")
    val statusMessage: String,
    @SerializedName("data")
    val data: MutableList<Any>?
)

data class Responses(
    @SerializedName("statusCode")
    val statusCode: Int,
    @SerializedName("statusMessage")
    val statusMessage: String,
    @SerializedName("data")
    val data: Single<List<LendTransactionDTO>>?
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

data class LendTransactionDTO(
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

data class ItemTypeEntity (
    @SerializedName("lendItemTypeID")
    val lendItemTypeID: Int,
    @SerializedName("lendItemTypeName")
    val lendItemTypeName: String,
    @SerializedName("lendItemCost")
    val lendItemCost: Double,
    @SerializedName("dateCreated")
    val dateCreated: String,
    @SerializedName("dateModified")
    val dateModified: Any
)

data class ItemOwnerProfileEntity (
    @SerializedName("lendItemOwnerProfileID")
    val lendItemOwnerProfileID: Int,
    @SerializedName("idNumber")
    val idNumber: String,
    @SerializedName("mobileNumber")
    val mobileNumber: String,
    @SerializedName("dateCreated")
    val dateCreated: String,
    @SerializedName("dateModified")
    val dateModified: Any,
    @SerializedName("customersEntity")
    val customersEntity: CustomersEntity
)

data class RentItemDTO (
    @SerializedName("lendItemID")
    val lendItemID: Int,
    @SerializedName("serialNumber")
    val serialNumber: String,
    @SerializedName("dateCreated")
    val dateCreated: String,
    @SerializedName("dateModified")
    val dateModified: Any,
    @SerializedName("itemTypeEntity")
    val itemTypeEntity: ItemTypeEntity,
    @SerializedName("itemOwnerProfileEntity")
    val itemOwnerProfileEntity: ItemOwnerProfileEntity
)
data class CustomersEntity (
    @SerializedName("customerID")
    val customerID: Int,
    @SerializedName("otherNames")
    val otherNames: String,
    @SerializedName("emailAddress")
    val emailAddress: String,
    @SerializedName("dateOfBirth")
    val dateOfBirth: String,
    @SerializedName("dateCreated")
    val dateCreated: String,
    @SerializedName("dateModified")
    val dateModified: String,
    @SerializedName("fname")
    val fname: String
)
data class UserDTO(
    val msisdn: String,
    val fname: String,
    val otherNames: String,
    val emailAddress: String,
    val dateOfBirth: String,
    val identificationNumber: String,
    val identificationType: Int
)