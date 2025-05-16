package com.github.piper.primitives.time

import java.time.OffsetDateTime

class OneOffSchedule(
    val start: OffsetDateTime,
)

class RecurringSchedule(
    val cron: String,
)