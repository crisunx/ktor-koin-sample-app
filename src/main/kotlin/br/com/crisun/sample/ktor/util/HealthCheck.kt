package br.com.crisun.sample.ktor.util

import com.fasterxml.jackson.databind.ObjectMapper
import io.ktor.application.ApplicationCallPipeline
import io.ktor.application.ApplicationFeature
import io.ktor.application.call
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.request.path
import io.ktor.response.respondText
import io.ktor.util.AttributeKey
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class HealthCheck(private val config: Configuration) {
    class Configuration {
        val checks = mutableMapOf<String, suspend () -> Status>()

        fun check(name: String, check: suspend () -> Status) {
            checks[name] = check
        }
    }

    fun intercept(pipeline: ApplicationCallPipeline) {
        val checks = config.checks

        if (checks.isEmpty()) return

        pipeline.intercept(ApplicationCallPipeline.Call) {
            val path = call.request.path().trim('/')

            if (path != "health") {
                return@intercept
            }

            call.respondText(checksResultsToJSON(checks), ContentType.Application.Json, HttpStatusCode.OK)
            finish()
        }
    }

    private suspend fun checksResultsToJSON(checks: MutableMap<String, suspend () -> Status>): String {
        return withContext(Dispatchers.IO) {
            ObjectMapper().writeValueAsString(checks.mapValues { it.value.invoke() })
        }
    }

    companion object Feature : ApplicationFeature<ApplicationCallPipeline, Configuration, HealthCheck> {
        override val key = AttributeKey<HealthCheck>("HealthCheck")

        override fun install(pipeline: ApplicationCallPipeline, configure: Configuration.() -> Unit): HealthCheck {
            val configuration = Configuration().apply(configure)
            return HealthCheck(configuration).also {
                it.intercept(pipeline)
            }
        }
    }
}
