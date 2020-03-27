package com.skills.rentaride.model

data class ResponseDTO (
    val statusCode: Int,
    val statusMessage: String,
    val resultData: MutableList<Any>?
)
