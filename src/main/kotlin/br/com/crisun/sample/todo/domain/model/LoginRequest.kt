package br.com.crisun.sample.todo.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(val username: String, val password: String)
