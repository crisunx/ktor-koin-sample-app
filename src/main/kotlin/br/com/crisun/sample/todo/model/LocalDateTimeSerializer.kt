package br.com.crisun.sample.todo.model

import kotlinx.serialization.Decoder
import kotlinx.serialization.Encoder
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializer
import java.time.LocalDateTime

@Serializer(forClass = LocalDateTime::class)
object LocalDateTimeSerializer : KSerializer<LocalDateTime> {
    override fun serialize(encoder: Encoder, value: LocalDateTime) { encoder.encodeString(value.toString()) }
    override fun deserialize(decoder: Decoder) = LocalDateTime.parse(decoder.decodeString())
}
