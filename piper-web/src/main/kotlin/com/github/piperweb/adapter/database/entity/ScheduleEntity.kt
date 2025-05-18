package com.github.piperweb.adapter.database.entity

import com.github.piper.primitives.time.Schedule
import com.github.piper.primitives.time.Schedule.OneOffSchedule
import com.github.piper.primitives.time.Schedule.RecurringSchedule
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.OffsetDateTime
import java.util.*

@Entity
@Table(name = "schedule")
class ScheduleEntity(schedule: Schedule) {
    @Id
    val id: UUID? = UUID.randomUUID()

    val scheduleType: ScheduleType = when (schedule) {
        is OneOffSchedule -> ScheduleType.ONE_OFF
        is RecurringSchedule -> ScheduleType.CRON
    }

    val cron: String? = when (schedule) {
        is RecurringSchedule -> schedule.cron
        is OneOffSchedule -> null
    }

    val oneOffSchedule: OffsetDateTime? = when (schedule) {
        is RecurringSchedule -> null
        is OneOffSchedule -> schedule.start
    }
}

enum class ScheduleType {
    CRON, ONE_OFF;

    fun fromSchedule(schedule: Schedule): ScheduleType = when (schedule) {
        is OneOffSchedule -> ONE_OFF
        is RecurringSchedule -> CRON
    }
}

