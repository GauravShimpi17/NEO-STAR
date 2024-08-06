    package com.example.registration.util.enumClass
enum class State(val state : String) {
    MAHARASHTRA("Maharashtra"),
    GUJARAT("Gujarat"),
    KARNATAKA("Karnataka"),
    MADHYA_PRADESH("Madhya Pradesh"),
    DELHI("Delhi"),
    NONE("None"),
    OTHERS("Others");

    override fun toString(): String {
        return state
    }
}

