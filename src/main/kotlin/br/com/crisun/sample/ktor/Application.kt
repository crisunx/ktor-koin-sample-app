package br.com.crisun.sample.ktor

import br.com.crisun.sample.ktor.service.MessageService
import io.ktor.application.call
import io.ktor.http.ContentType
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.Routing
import io.ktor.routing.get
import io.ktor.server.engine.commandLineEnvironment
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import mu.KotlinLogging
import org.koin.ktor.ext.inject

val logger = KotlinLogging.logger {}

fun main(args: Array<String>) {
    embeddedServer(Netty, commandLineEnvironment(args)).start()
}

fun Routing.root() {
    val service by inject<MessageService>()

    get("/crisun") {

        logger.info { "This is logging of - kotlin-logging" }

        call.respond(service.getMessages())
    }
}
