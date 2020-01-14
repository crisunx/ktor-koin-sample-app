package br.com.crisun.sample.ktor.configuration

import br.com.crisun.sample.ktor.di.appModule
import br.com.crisun.sample.ktor.root
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.viartemev.ktor.flyway.FlywayFeature
import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.features.CallLogging
import io.ktor.features.Compression
import io.ktor.features.ContentNegotiation
import io.ktor.features.DefaultHeaders
import io.ktor.http.ContentType
import io.ktor.jackson.JacksonConverter
import io.ktor.jackson.jackson
import io.ktor.routing.routing
import org.koin.Logger.slf4jLogger
import org.koin.ktor.ext.Koin
import java.util.*

fun Application.module() {
    install(CallLogging)
    install(Compression)
    install(DefaultHeaders)
    install(Koin) {
        slf4jLogger()
        modules(appModule)
    }
    install(FlywayFeature) {
        dataSource = DatabaseFactory.hikari()
    }
    install(ContentNegotiation) {
        jackson {
            register(ContentType.Application.Json, JacksonConverter(appJacksonMapper))
        }
    }

    routing { root() }
}

val appJacksonMapper: ObjectMapper = jacksonObjectMapper().apply {
    configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
    setSerializationInclusion(JsonInclude.Include.NON_NULL)
    setTimeZone(TimeZone.getTimeZone("America/Sao_Paulo"))
    registerModule(JavaTimeModule())
}
