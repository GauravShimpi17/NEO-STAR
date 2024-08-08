package com.example.registration.view.util.enumClass

enum class Education(private val education: String) {
    NONE("Select Education Level"),
    POST_GRADUATE("Post Graduate"),
    GRADUATE("Graduate"),
    HSC_DIPLOMA("HSC/Diploma"),
    SSC("SSC");

    override fun toString(): String {
        return education
    }

    companion object {
        private val map = mutableMapOf<String, Education>()
        init {
            Education.entries.forEach {
                map[it.toString()] = it
            }
        }
        fun getEducation(education: String): Education {
            return map.getOrDefault(education, NONE)
        }
    }
}