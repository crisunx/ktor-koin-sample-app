package br.com.crisun.sample.todo.routes

import br.com.crisun.sample.todo.configuration.AuthConfig
import br.com.crisun.sample.todo.service.UserService
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Routing
import io.ktor.routing.post
import io.ktor.util.KtorExperimentalAPI
import org.koin.ktor.ext.inject

@KtorExperimentalAPI
fun Routing.auth() {
    val jwt by inject<AuthConfig>()
    val service by inject<UserService>()

    post("/user/login") {
        call.respond(HttpStatusCode.Created, mapOf("token" to jwt.sign(service.findUser(call.receive()))))
    }
}
