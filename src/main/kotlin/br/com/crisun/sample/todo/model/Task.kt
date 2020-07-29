package br.com.crisun.sample.todo.model

import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class Task(
    val id: Long = 0,
    val title: String,
    val description: String,
    @kotlinx.serialization.Serializable(with = LocalDateTimeSerializer::class)
    val createDate: LocalDateTime = LocalDateTime.now(),
    @kotlinx.serialization.Serializable(with = LocalDateTimeSerializer::class)
    val startDate: LocalDateTime? = null,
    @kotlinx.serialization.Serializable(with = LocalDateTimeSerializer::class)
    val finishDate: LocalDateTime? = null,
    val status: Status = Status.CREATED
)
