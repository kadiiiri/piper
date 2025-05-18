package com.github.piperweb.domain.mapper

import com.github.piper.primitives.time.Schedule.OneOffSchedule
import com.github.piper.primitives.time.Schedule.RecurringSchedule
import com.github.piperweb.adapter.database.entity.DagEntity
import com.github.piperweb.adapter.database.entity.ScheduleEntity
import com.github.piperweb.adapter.database.entity.ScheduleType.CRON
import com.github.piperweb.adapter.database.entity.ScheduleType.ONE_OFF
import com.github.piperweb.domain.model.Dag

object DagMapper {
    fun Dag.toEntity() = DagEntity(id, name, createdAt, status, ScheduleEntity(schedule))

    fun DagEntity.toModel() = Dag(id!!, name, createdAt, status, schedule.toModel())

    fun ScheduleEntity.toModel() = when (scheduleType) {
        ONE_OFF -> OneOffSchedule(this.oneOffSchedule!!)
        CRON -> RecurringSchedule(this.cron!!)
    }
}