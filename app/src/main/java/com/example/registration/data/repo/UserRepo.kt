package com.example.registration.data.repo

import com.example.registration.data.model.AddressData
import com.example.registration.data.model.PrimaryData
import com.example.registration.data.model.ProfessionalData

interface UserRepo {
    fun saveUser(primaryData: PrimaryData, professionalData: ProfessionalData, addressData: AddressData)

    fun getUsers() :List<PrimaryData>
}