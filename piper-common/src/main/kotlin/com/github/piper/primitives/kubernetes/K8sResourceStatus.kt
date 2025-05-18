package com.github.piper.primitives.kubernetes

enum class K8sResourceStatus {
    ENABLED,
    DISABLED,
    DELETED,

    AWAITING_EXECUTION,
    FINISHED,
    UNKNOWN
    ;

    companion object {
        fun fromString(value: String?): K8sResourceStatus {
            return entries.find { it.name.equals(value, ignoreCase = true) } ?: UNKNOWN
        }
    }
}
