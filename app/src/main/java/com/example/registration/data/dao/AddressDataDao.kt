package com.example.registration.data.dao

import androidx.room.Dao
import androidx.room.Upsert
import com.example.registration.data.model.AddressData

@Dao
interface AddressDataDao {

    @Upsert
    fun insertAddressInfo(addressData: AddressData)
}