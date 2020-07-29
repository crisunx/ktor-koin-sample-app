package br.com.crisun.sample.todo.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: Int,
    val name: String,
    val username: String,
    val password: String,
    val roles: List<String>
)
