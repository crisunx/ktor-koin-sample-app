package br.com.crisun.sample.todo.model

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
@ExperimentalSerializationApi
data class Task(
    val id: Long = 0,
    val title: String,
    val description: String,
    @Serializable(with = LocalDateTimeSerializer::class)
    val createDate: LocalDateTime = LocalDateTime.now(),
    @Serializable(with = LocalDateTimeSerializer::class)
    val startDate: LocalDateTime? = null,
    @Serializable(with = LocalDateTimeSerializer::class)
    val finishDate: LocalDateTime? = null,
    val status: Status = Status.CREATED
)
