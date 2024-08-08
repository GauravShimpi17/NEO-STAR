    package com.example.registration.view.util.enumClass
enum class State(private val state : String) {
    NONE("Select State"),
    MAHARASHTRA("Maharashtra"),
    GUJARAT("Gujarat"),
    KARNATAKA("Karnataka"),
    MADHYA_PRADESH("Madhya Pradesh"),
    DELHI("Delhi"),
    OTHERS("Others");

    override fun toString(): String {
        return state
    }
    companion object {
        private val map = mutableMapOf<String, State>()
        init {
            State.entries.forEach {
                map[it.toString()] = it
            }
        }
        fun getEducation(state: String): State {
            return map.getOrDefault(state, NONE)
        }
    }
}

