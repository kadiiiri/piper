package com.github.piper.primitives.time

import com.cronutils.model.Cron
import com.cronutils.model.CronType.UNIX
import com.cronutils.model.definition.CronDefinitionBuilder
import com.cronutils.model.time.ExecutionTime
import com.cronutils.parser.CronParser
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import java.time.OffsetDateTime

/**
 * Represents a schedule for executing tasks.
 * This can be either a one-time execution or a recurring execution based on a cron expression.
 */
@JsonDeserialize(using = ScheduleDeserializer::class)
@JsonSerialize(using = ScheduleSerializer::class)
sealed interface Schedule {
    /**
     * Represents a schedule that executes once at a specific time.
     *
     * @property start The date and time when the task should be executed
     */
    class OneOffSchedule(val start: OffsetDateTime) : Schedule {
        override fun nextExecutionTime(): OffsetDateTime = start
    }

    /**
     * Represents a schedule that executes repeatedly according to a cron expression.
     *
     * @property cron The cron expression defining the execution schedule
     */
    class RecurringSchedule(val cron: String) : Schedule {
        override fun nextExecutionTime(): OffsetDateTime? {
            val cronDefinition = CronDefinitionBuilder.instanceDefinitionFor(UNIX)
            val parser = CronParser(cronDefinition)
            val parsedCron: Cron = parser.parse(cron)

            val now = OffsetDateTime.now()

            val executionTime = ExecutionTime.forCron(parsedCron)
            val nextExecution = executionTime.nextExecution(now.toZonedDateTime())

            return nextExecution.orElse(null)?.toOffsetDateTime()
        }


    }

    fun nextExecutionTime(): OffsetDateTime?


}