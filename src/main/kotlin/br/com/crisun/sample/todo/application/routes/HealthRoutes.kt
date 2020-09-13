package br.com.crisun.sample.todo.application.routes

import io.ktor.application.call
import io.ktor.response.respond
import io.ktor.routing.Routing
import io.ktor.routing.get

fun Routing.health() {
    get("/health") {
        call.respond(mapOf("status" to "UP"))
    }
}
