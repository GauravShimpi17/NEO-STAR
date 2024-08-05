package com.example.registration.model

import com.example.registration.util.Education

data class ProfessionalInfo(
    var education : Education = Education.NONE,
    var yearOfPassing : String = "",
    var grade : String = "",
    var experience : String = "",
    var designation : String = "",
    var domain : String = ""
)
