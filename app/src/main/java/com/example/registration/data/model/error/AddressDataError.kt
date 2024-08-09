package com.example.registration.data.model.error

data class AddressDataError(
    val addressError : String? = null,
    val landmarkError : String? = null,
    val cityNameError : String? = null,
    val stateError : String? = null,
    val pinCodeError : String? = null,
)
