package com.example.registration.data.dao

import android.location.Address
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.registration.data.model.AddressData
import com.example.registration.data.model.PrimaryData

@Dao
interface AddressDataDao {

    @Upsert
    fun insertAddressInfo(addressData: AddressData)

}