package com.example.registration.model

import com.example.registration.util.State

data class AddressData(
    var address : String = "",
    var landmark : String = "",
    var city : String = "",
    var state : State = State.OTHERS,
    var pinCode : String = ""
)
