package com.example.registration.model

import androidx.room.Entity
import com.example.registration.util.enumClass.State

@Entity(tableName = "address_data")
data class AddressData(

    var address : String = "",
    var landmark : String = "",
    var city : String = "",
    var state : State = State.OTHERS,
    var pinCode : String = ""
)
