package br.com.crisun.sample.todo.routes

import br.com.crisun.sample.todo.configuration.module
import io.ktor.application.Application
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.handleRequest
import io.ktor.server.testing.withTestApplication
import io.ktor.util.KtorExperimentalAPI
import org.junit.Before
import org.junit.Test
import org.koin.test.AutoCloseKoinTest

class HealthRoutesKtTest : AutoCloseKoinTest() {
    @Before
    fun setUp() {
        System.setProperty("SECRET", "ayla")
    }

    @Test
    @KtorExperimentalAPI
    fun `Health check test`() {
        withTestApplication(Application::module) {
            with(handleRequest(HttpMethod.Get, "/health")) {
                kotlin.test.assertEquals(HttpStatusCode.OK, response.status())
                kotlin.test.assertEquals("""{"status":"UP"}""", response.content)
            }
        }
    }
}
