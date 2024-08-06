package com.example.registration.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "primary_info")
data class PrimaryData(
    @PrimaryKey
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
