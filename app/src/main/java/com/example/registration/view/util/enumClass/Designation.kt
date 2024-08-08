package com.example.registration.view.util.enumClass

enum class Designation(private val designation :String) {
    NONE("Select Designation"),
    SENIOR_DEVELOPER("Senior Developer"),
    JUNIOR_DEVELOPER("Junior Developer"),
    TRAINEE_ENGINEER("Trainee Engineer");

    override fun toString(): String {
        return designation
    }

    companion object {
        private val map = mutableMapOf<String, Designation>()
        init {
            Designation.entries.forEach {
                map[it.toString()] = it
            }
        }
        fun getEducation(designation: String): Designation {
            return map.getOrDefault(designation, NONE)
        }
    }
}