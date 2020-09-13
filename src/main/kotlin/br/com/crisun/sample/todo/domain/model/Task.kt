package br.com.crisun.sample.todo.domain.model

import br.com.crisun.sample.todo.application.configuration.LocalDateTimeSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
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
