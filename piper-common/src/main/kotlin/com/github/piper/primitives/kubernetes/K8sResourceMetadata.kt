package com.github.piper.primitives.kubernetes

import java.time.OffsetDateTime

data class K8sResourceMetadata(
    val name: String,
    val creationTimestamp: OffsetDateTime,
    val lastUpdatedTimeStamp: OffsetDateTime,
)