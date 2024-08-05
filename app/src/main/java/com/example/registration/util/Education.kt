package com.example.registration.util

enum class Education(val education: String) {
    NONE("None"),
    POST_GRADUATE("Post Graduate"),
    GRADUATE("Graduate"),
    HSC_DIPLOMA("HSC/Diploma"),
    SSC("SSC");

    override fun toString(): String {
        return education
    }

}