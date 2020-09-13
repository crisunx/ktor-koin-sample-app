package br.com.crisun.sample.todo.application.configuration

import br.com.crisun.sample.todo.application.di.appModule
import br.com.crisun.sample.todo.application.di.jsonModule
import br.com.crisun.sample.todo.application.exception.NotFoundException
import br.com.crisun.sample.todo.application.routes.auth
import br.com.crisun.sample.todo.application.routes.health
import br.com.crisun.sample.todo.application.routes.todo
import br.com.crisun.sample.todo.data.di.dataModule
import br.com.crisun.sample.todo.domain.di.domainModule
import com.viartemev.ktor.flyway.FlywayFeature
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.auth.authentication
import io.ktor.auth.jwt.JWTPrincipal
import io.ktor.auth.jwt.jwt
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.response.respond
import io.ktor.routing.routing
import io.ktor.serialization.json
import io.ktor.util.KtorExperimentalAPI
import kotlinx.serialization.json.Json
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
                jsonModule,
                dataModule,
                domainModule
            )
        )
    }

    install(FlywayFeature) {
        this.dataSource = dataSource
    }

    install(ContentNegotiation) {
        json(
            contentType = ContentType.Application.Json,
            json = Json {
                ignoreUnknownKeys = true
            }
        )
    }

    install(StatusPages) {
        exception<Throwable> { cause ->
            call.respond(HttpStatusCode.InternalServerError, "Internal Server Error")
            throw cause
        }
        exception<NotFoundException> { cause ->
            call.respond(HttpStatusCode.NotFound)
            throw cause
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
