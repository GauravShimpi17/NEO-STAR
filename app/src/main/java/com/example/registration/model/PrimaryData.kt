package com.example.registration.model

data class PrimaryData(
    var id : String="",
    var firstName : String="",
    var lastName : String="",
    var phoneNumber : String="",
    var email : String = "",
    var gender : String = "",
    var password : String = "",
    var confirmPassword : String = "",
    var professionalInfo: ProfessionalInfo= ProfessionalInfo(),
    var addressData: AddressData=AddressData()

)
