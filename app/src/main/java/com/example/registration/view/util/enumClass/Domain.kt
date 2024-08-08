package com.example.registration.view.util.enumClass

enum class Domain(private val domain: String) {
    NONE("Select your domain"),
    SOFTWARE_DEVELOPMENT("Software Development"),
    DATA_SCIENCE("Data Science"),
    DEVOPS("DevOps");

    override fun toString(): String {
        return domain
    }

    companion object {
        private val map = mutableMapOf<String, Domain>()
        init {
            Domain.entries.forEach {
                map[it.toString()] = it
            }
        }
        fun getEducation(domain: String): Domain {
            return map.getOrDefault(domain, NONE)
        }
    }
}
