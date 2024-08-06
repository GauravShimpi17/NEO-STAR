package com.example.registration.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "professional_info")
data class ProfessionalData(
    var primaryInfoId: Long = 0, // Foreign key to primary_info
    var education: String = "",
    var yearOfPassing: String = "",
    var grade: String = "",
    var experience: String = "",
    var designation: String = "",
    var domain: String = "",
    @PrimaryKey(autoGenerate = true) var professionalId: Long = 0
)
