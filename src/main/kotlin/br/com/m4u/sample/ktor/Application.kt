package br.com.m4u.sample.ktor

import br.com.m4u.sample.ktor.di.appModule
import br.com.m4u.sample.ktor.service.Service
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.CallLogging
import io.ktor.features.DefaultHeaders
import io.ktor.http.ContentType
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.server.engine.commandLineEnvironment
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import org.koin.Logger.slf4jLogger
import org.koin.ktor.ext.Koin
import org.koin.ktor.ext.inject

fun main(args: Array<String>) {
    embeddedServer(Netty, commandLineEnvironment(args)).start()
}

fun Application.main() {
    install(CallLogging)
    install(DefaultHeaders)
    install(Koin) {
        slf4jLogger()
        modules(appModule)
    }

    val service by inject<Service>()

    routing {
        get("/") {
            call.respondText(service.getTitle(), ContentType.Application.Json)
        }
    }
}