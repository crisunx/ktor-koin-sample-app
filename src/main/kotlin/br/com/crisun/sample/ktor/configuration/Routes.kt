package br.com.crisun.sample.ktor.configuration

import br.com.crisun.sample.ktor.exception.ValidationException
import br.com.crisun.sample.ktor.model.Message
import br.com.crisun.sample.ktor.service.MessageService
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.request.receiveOrNull
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.*
import org.koin.ktor.ext.inject

fun Routing.root() {
    val service by inject<MessageService>()
    val idCantBeNullException = ValidationException("Id parameter can't be null")

    get("/messages") {
        call.respond(service.findAll())
    }

    get("/message/{id}") {
        val id = call.parameters["id"] ?: throw idCantBeNullException

        service.findById(id)?.let { call.respond(HttpStatusCode.OK, it) } ?: call.respond(HttpStatusCode.NotFound)
    }

    post("/message") {
        call.respond(HttpStatusCode.Created, service.insert(call.receiveOrNull()))
    }

    put("/message/{id}") {
        val id = call.parameters["id"] ?: throw idCantBeNullException
        val message = call.receiveOrNull<Message>() ?: throw ValidationException("Message parameter can't be null")

        service.update(id.toInt(), message).let { call.respond(HttpStatusCode.OK) }
    }

    delete("/message/{id}") {
        val id = call.parameters["id"] ?: throw idCantBeNullException

        service.delete(id.toInt()).let { call.respond(HttpStatusCode.OK) }
    }

    get("/health") {
        call.respondText("""{"status": "UP"}""")
    }
}
