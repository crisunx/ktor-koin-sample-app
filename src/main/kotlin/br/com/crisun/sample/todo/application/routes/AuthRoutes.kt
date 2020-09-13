package br.com.crisun.sample.todo.application.routes

import br.com.crisun.sample.todo.application.configuration.AuthConfig
import br.com.crisun.sample.todo.domain.usecase.UserUseCase
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.util.*
import org.koin.ktor.ext.inject

@KtorExperimentalAPI
fun Routing.auth() {
    val jwt by inject<AuthConfig>()
    val service by inject<UserUseCase>()

    post("/user/login") {
        call.respond(HttpStatusCode.Created, mapOf("token" to jwt.sign(service.findUser(call.receive()))))
    }
}
