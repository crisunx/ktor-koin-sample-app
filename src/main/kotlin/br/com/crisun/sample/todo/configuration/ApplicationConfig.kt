package br.com.crisun.sample.todo.configuration

import br.com.crisun.sample.todo.di.appModule
import br.com.crisun.sample.todo.di.authModule
import br.com.crisun.sample.todo.di.jsonModule
import br.com.crisun.sample.todo.routes.auth
import br.com.crisun.sample.todo.routes.health
import br.com.crisun.sample.todo.routes.todo
import com.viartemev.ktor.flyway.FlywayFeature
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.auth.authentication
import io.ktor.auth.jwt.JWTPrincipal
import io.ktor.auth.jwt.jwt
import io.ktor.features.*
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import io.ktor.routing.routing
import io.ktor.serialization.json
import io.ktor.util.KtorExperimentalAPI
import kotlinx.serialization.json.JsonConfiguration
import org.koin.ktor.ext.Koin
import org.koin.ktor.ext.inject
import org.slf4j.event.Level
import javax.sql.DataSource

@KtorExperimentalAPI
fun Application.module() {
    val jwt by inject<AuthConfig>()
    val dataSource by inject<DataSource>()

    install(Compression)

    install(CallLogging) { level = Level.INFO }

    install(DefaultHeaders) {
        header("X-Developer", "crisun")
    }

    install(Koin) {
        modules(
            listOf(
                appModule,
                authModule,
                jsonModule
            )
        )
    }

    install(FlywayFeature) {
        this.dataSource = dataSource
    }

    install(ContentNegotiation) {
        json(
            json = JsonConfiguration.Stable.copy(
                ignoreUnknownKeys = true
            )
        )
    }

    install(StatusPages) {
        exception<Throwable> { cause ->
            call.respond(HttpStatusCode.InternalServerError, "Internal Server Error")
            throw cause
        }
        exception<NotFoundException> {
            call.respond(HttpStatusCode.NotFound)
        }
    }

    install(CORS) {
        anyHost()
        allowCredentials = true
        allowNonSimpleContentTypes = true
        method(HttpMethod.Get)
        method(HttpMethod.Post)
        method(HttpMethod.Put)
        method(HttpMethod.Delete)
        method(HttpMethod.Patch)
        method(HttpMethod.Options)
        header(HttpHeaders.ContentType)
        header(HttpHeaders.Authorization)
        header(HttpHeaders.AccessControlAllowHeaders)
        header(HttpHeaders.AccessControlAllowOrigin)
    }

    authentication {
        jwt {
            realm = "todo-list-bff"

            verifier(jwt.verifier)

            validate { credentials ->
                if (credentials.payload.claims.contains("user")) {
                    JWTPrincipal(credentials.payload)
                } else {
                    null
                }
            }
        }
    }

    routing {
        auth()
        todo()
        health()
    }
}
