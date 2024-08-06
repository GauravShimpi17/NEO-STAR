package com.example.registration.data.repo

import com.example.registration.data.database.AppDatabase
import com.example.registration.data.model.AddressData
import com.example.registration.data.model.PrimaryData
import com.example.registration.data.model.ProfessionalData

class LocalUserRepo(private val database: AppDatabase) : UserRepo {
    override fun saveUser(
        primaryData: PrimaryData,
        professionalData: ProfessionalData,
        addressData: AddressData
    ) {
        val id = database.primaryDataDao().savePrimaryData(primaryData)
        database.professionalDataDao().insertProfessionalInfo(professionalData.copy(primaryInfoId = id))
        database.addressDao().insertAddressInfo(addressData.copy(primaryInfoId = id))
    }

    override fun getUsers(): List<PrimaryData> {
        TODO("Not yet implemented")
    }
}