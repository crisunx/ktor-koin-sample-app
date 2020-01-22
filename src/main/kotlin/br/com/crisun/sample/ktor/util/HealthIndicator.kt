package br.com.crisun.sample.ktor.util

enum class Status {
    UNKNOWN, UP, DOWN
}

abstract class HealthIndicator {
    abstract suspend fun doHealthCheck(): Status
}