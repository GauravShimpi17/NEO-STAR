package com.example.registration.data.model.error


data class PrimaryDataError(
    val firstNameError : String? = null,
    val lastNameError : String? = null,
    val phoneNumberError : String? = null,
    val emailError : String? = null,
    val passwordError : String? = null,
    val confirmPasswordError : String? = null
)
