package com.example.registration.model

import androidx.room.Entity
import com.example.registration.util.enumClass.Education

@Entity(tableName = "professional_info")
data class ProfessionalInfo(
    var education : Education = Education.NONE,
    var yearOfPassing : String = "",
    var grade : String = "",
    var experience : String = "",
    var designation : String = "",
    var domain : String = ""
)
