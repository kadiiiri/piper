package com.github.piper.primitives.jackson

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.github.piper.primitives.time.ScheduleJacksonModule

/**
 * Utility class to configure Jackson ObjectMapper for the Piper project.
 * This registers all custom serializers and deserializers, as well as the JavaTimeModule
 * to handle Java 8 date/time types.
 */
object PiperJacksonConfig {
    /**
     * Configures the provided ObjectMapper with all necessary modules for the Piper project.
     * 
     * @param objectMapper The ObjectMapper to configure
     * @return The configured ObjectMapper
     */
    fun configure(objectMapper: ObjectMapper): ObjectMapper {
        return objectMapper
            .registerModule(ScheduleJacksonModule())
            .registerModule(JavaTimeModule())
    }
}
