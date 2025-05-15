package com.eska.piperweb.domain.model


enum class ResourceStatus {
    ENABLED,
    DISABLED,
    DELETED,

    ACTIVE,
    COMPLETED,
    FAILED,
    UNKNOWN
    ;

    companion object {
        fun fromString(value: String?): ResourceStatus {
            return entries.find { it.name.equals(value, ignoreCase = true) } ?: UNKNOWN
        }
    }
}
