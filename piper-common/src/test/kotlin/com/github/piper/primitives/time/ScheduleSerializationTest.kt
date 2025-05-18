package com.github.piper.primitives.time

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.github.piper.primitives.jackson.PiperJacksonConfig
import com.github.piper.primitives.time.Schedule.OneOffSchedule
import com.github.piper.primitives.time.Schedule.RecurringSchedule
import java.time.OffsetDateTime
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ScheduleSerializationTest {

    private val objectMapper = PiperJacksonConfig.configure(ObjectMapper().registerKotlinModule())

    @Test
    fun `test OneOffSchedule serialization and deserialization`() {
        val now = OffsetDateTime.now()
        val schedule = OneOffSchedule(now)

        val json = objectMapper.writeValueAsString(schedule)
        println("[DEBUG_LOG] Serialized OneOffSchedule: $json")

        val deserialized = objectMapper.readValue(json, Schedule::class.java)
        println("[DEBUG_LOG] Deserialized class: ${deserialized.javaClass.simpleName}")

        assertEquals(schedule.javaClass, deserialized.javaClass)
        assertEquals(schedule.start.toInstant(), (deserialized as OneOffSchedule).start.toInstant())
    }

    @Test
    fun `test RecurringSchedule serialization and deserialization`() {
        val cronExpression = "0 0 * * *" // Run at midnight every day
        val schedule = RecurringSchedule(cronExpression)

        val json = objectMapper.writeValueAsString(schedule)
        println("[DEBUG_LOG] Serialized RecurringSchedule: $json")

        val deserialized = objectMapper.readValue(json, Schedule::class.java)
        println("[DEBUG_LOG] Deserialized class: ${deserialized.javaClass.simpleName}")

        assertEquals(schedule.javaClass, deserialized.javaClass)
        assertEquals(schedule.cron, (deserialized as RecurringSchedule).cron)
    }
}
