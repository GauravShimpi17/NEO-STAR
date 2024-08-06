package com.example.registration.data.model

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "primary_info")
data class PrimaryData(
    var firstName: String = "",
    var lastName: String = "",
    var phoneNumber: String = "",
    var email: String = "",
    var gender: String = "",
    var password: String = "",
    var confirmPassword: String = "",
    var image: ByteArray? = null, // Store image as a BLOB
    @PrimaryKey(autoGenerate = true)
    var primaryInfoId: Long = 0
)
