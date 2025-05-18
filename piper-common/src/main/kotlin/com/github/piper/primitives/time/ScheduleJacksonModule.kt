package com.github.piper.primitives.time

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.module.SimpleModule
import com.github.piper.primitives.time.Schedule.OneOffSchedule
import com.github.piper.primitives.time.Schedule.RecurringSchedule
import java.time.OffsetDateTime

/**
 * Jackson module for serializing and deserializing Schedule objects.
 * This module registers custom serializers and deserializers for the Schedule sealed interface.
 */
class ScheduleJacksonModule : SimpleModule() {
    init {
        addSerializer(Schedule::class.java, ScheduleSerializer())
        addDeserializer(Schedule::class.java, ScheduleDeserializer())
    }
}

/**
 * Custom serializer for the Schedule sealed interface.
 * This serializer handles both OneOffSchedule and RecurringSchedule implementations.
 */
class ScheduleSerializer : JsonSerializer<Schedule>() {
    override fun serialize(schedule: Schedule, gen: JsonGenerator, provider: SerializerProvider) {
        gen.writeStartObject()

        when (schedule) {
            is OneOffSchedule -> {
                gen.writeStringField("type", "oneOff")
                gen.writeObjectField("start", schedule.start)
            }
            is RecurringSchedule -> {
                gen.writeStringField("type", "recurring")
                gen.writeStringField("cron", schedule.cron)
            }
        }

        gen.writeEndObject()
    }
}

/**
 * Custom deserializer for the Schedule sealed interface.
 * This deserializer determines which implementation to use based on the "type" field in the JSON.
 */
class ScheduleDeserializer : JsonDeserializer<Schedule>() {
    override fun deserialize(p: JsonParser, ctxt: DeserializationContext): Schedule {
        val node: JsonNode = p.codec.readTree(p)

        return when (val type = node.get("type")?.asText()) {
            "oneOff" -> {
                val start = node.get("start")?.let {
                    ctxt.readTreeAsValue(it, OffsetDateTime::class.java)
                } ?: throw IllegalArgumentException("OneOffSchedule requires a 'start' field")

                OneOffSchedule(start)
            }
            "recurring" -> {
                val cron = node.get("cron")?.asText()
                    ?: throw IllegalArgumentException("RecurringSchedule requires a 'cron' field")

                RecurringSchedule(cron)
            }
            else -> throw IllegalArgumentException("Unknown schedule type: $type")
        }
    }
}
