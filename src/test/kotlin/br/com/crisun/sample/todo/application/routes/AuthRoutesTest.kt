package br.com.crisun.sample.todo.application.routes

import br.com.crisun.sample.todo.application.configuration.module
import io.ktor.application.Application
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.handleRequest
import io.ktor.server.testing.setBody
import io.ktor.server.testing.withTestApplication
import io.ktor.util.KtorExperimentalAPI
import org.junit.Before
import org.junit.Test
import org.koin.test.AutoCloseKoinTest
import kotlin.test.assertEquals

class AuthRoutesTest : AutoCloseKoinTest() {

    @Before
    fun setUp() {
        System.setProperty("SECRET", "ayla")
    }

    @Test
    @KtorExperimentalAPI
    fun `Create todo with success`() {
        withTestApplication(Application::module) {
            with(
                handleRequest(HttpMethod.Post, "/user/login") {
                    addHeader("Content-type", "application/json")
                    setBody("""{"username":"crisun","password":"r4e3w2q1"}""")
                }
            ) {
                assertEquals(HttpStatusCode.Created, response.status())
            }
        }
    }
}
