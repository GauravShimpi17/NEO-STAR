package com.example.registration.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.registration.view.util.enumClass.State

@Entity(tableName = "address_data")
data class AddressData(
    var primaryInfoId: Long = 0,
    var address: String = "",
    var landmark: String = "",
    var city: String = "",
    var state: String = "",
    var pinCode: String = "",
    @PrimaryKey(autoGenerate = true) var addressId: Long = 0
)