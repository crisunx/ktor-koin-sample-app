package br.com.crisun.sample.ktor.configuration

import br.com.crisun.sample.ktor.di.appModule
import br.com.crisun.sample.ktor.exception.BaseHttpException
import br.com.crisun.sample.ktor.util.DatabaseCheck
import br.com.crisun.sample.ktor.util.HealthCheck
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.viartemev.ktor.flyway.FlywayFeature
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.*
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.jackson.JacksonConverter
import io.ktor.jackson.jackson
import io.ktor.response.respond
import io.ktor.routing.routing
import io.ktor.util.KtorExperimentalAPI
import java.time.ZonedDateTime
import java.util.*
import javax.sql.DataSource
import org.koin.Logger.slf4jLogger
import org.koin.dsl.module
import org.koin.ktor.ext.Koin
import org.koin.ktor.ext.inject

@KtorExperimentalAPI
fun Application.module() {
    val data by inject<DataSource>()
    val databaseCheck by inject<DatabaseCheck>()
    val ktorModule = module { single { environment } }

    install(CallLogging)
    install(Compression)
    install(HealthCheck) {
        check("database") { databaseCheck.doHealthCheck() }
    }
    install(DefaultHeaders)
    install(Koin) {
        slf4jLogger()
        modules(listOf(ktorModule, appModule))
    }
    install(StatusPages) {
        exception<BaseHttpException> {
            call.respond(
                HttpStatusCode.fromValue(it.httpStatus),
                mapOf(
                    "status" to it.httpStatus,
                    "message" to (it.message ?: ""),
                    "timestamp" to ZonedDateTime.now().toString()
                )
            )
        }
    }
    install(FlywayFeature) {
        dataSource = data
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
