package br.com.crisun.sample.todo.application.routes

import br.com.crisun.sample.todo.application.configuration.module
import br.com.crisun.sample.todo.application.exception.NotFoundException
import br.com.crisun.sample.todo.application.exception.ValidationException
import br.com.crisun.sample.todo.domain.model.Task
import io.ktor.application.Application
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.handleRequest
import io.ktor.server.testing.setBody
import io.ktor.server.testing.withTestApplication
import io.ktor.util.KtorExperimentalAPI
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.*
import org.junit.Before
import org.junit.Test
import org.koin.test.AutoCloseKoinTest
import kotlin.test.assertEquals

class TodoRoutesTest : AutoCloseKoinTest() {
    private val json = Json { ignoreUnknownKeys = true }

    @Before
    fun setUp() {
        System.setProperty("SECRET", "crisun")
    }

    @Test
    @KtorExperimentalAPI
    fun `Create todo with success`() {
        var token: String

        withTestApplication(Application::module) {
            with(
                handleRequest(HttpMethod.Post, "/user/login") {
                    addHeader("Content-type", "application/json")
                    setBody("""{"username":"crisun","password":"r4e3w2q1"}""")
                }
            ) {
                assertEquals(HttpStatusCode.Created, response.status())

                token = parseToken(response.content.toString())
            }

            with(
                handleRequest(HttpMethod.Post, "/todo") {
                    addHeader("Content-type", "application/json")
                    addHeader("Authorization", "Bearer $token")
                    setBody("""{"id":0,"title":"Tarefa 1","description":"Arrumar a casa"}""")
                }
            ) {
                assertEquals(HttpStatusCode.Created, response.status())
            }
        }
    }

    @Test
    @KtorExperimentalAPI
    fun `Update todo with success`() {
        var task: Task
        var token: String

        withTestApplication(Application::module) {
            with(
                handleRequest(HttpMethod.Post, "/user/login") {
                    addHeader("Content-type", "application/json")
                    setBody("""{"username":"crisun","password":"r4e3w2q1"}""")
                }
            ) {
                assertEquals(HttpStatusCode.Created, response.status())

                token = parseToken(response.content.toString())
            }

            with(
                handleRequest(HttpMethod.Post, "/todo") {
                    addHeader("Content-type", "application/json")
                    addHeader("Authorization", "Bearer $token")
                    setBody("""{"id":0,"title":"Tarefa 1","description":"Arrumar a casa"}""")
                }
            ) {
                assertEquals(HttpStatusCode.Created, response.status())

                task = json.decodeFromString(Task.serializer(), response.content.toString())
            }

            with(
                handleRequest(HttpMethod.Put, "/todo/${task.id}") {
                    addHeader("Content-type", "application/json")
                    addHeader("Authorization", "Bearer $token")
                    setBody("""{"id":${task.id},"title":"Tarefa Alterada","description":"Arrumar a casa 2"}""")
                }
            ) {
                assertEquals(HttpStatusCode.OK, response.status())
            }
        }
    }

    @KtorExperimentalAPI
    @ExperimentalSerializationApi
    @Test(expected = NotFoundException::class)
    fun `Try to update todo with error`() {
        var task: Task
        var token: String

        withTestApplication(Application::module) {
            with(
                handleRequest(HttpMethod.Post, "/user/login") {
                    addHeader("Content-type", "application/json")
                    setBody("""{"username":"crisun","password":"r4e3w2q1"}""")
                }
            ) {
                assertEquals(HttpStatusCode.Created, response.status())

                token = parseToken(response.content.toString())
            }

            with(
                handleRequest(HttpMethod.Post, "/todo") {
                    addHeader("Content-type", "application/json")
                    addHeader("Authorization", "Bearer $token")
                    setBody("""{"id":0,"title":"Tarefa 1","description":"Arrumar a casa"}""")
                }
            ) {
                assertEquals(HttpStatusCode.Created, response.status())

                task = json.decodeFromString(Task.serializer(), response.content.toString())
            }

            with(
                handleRequest(HttpMethod.Put, "/todo/${Int.MAX_VALUE}") {
                    addHeader("Content-type", "application/json")
                    addHeader("Authorization", "Bearer $token")
                    setBody("""{"id":${task.id},"title":"Tarefa Alterada","description":"Arrumar a casa 2"}""")
                }
            ) {
                assertEquals(HttpStatusCode.NotFound, response.status())
            }
        }
    }

    @Test
    @KtorExperimentalAPI
    fun `Remove todo with success`() {
        var task: Task
        var token: String

        withTestApplication(Application::module) {
            with(
                handleRequest(HttpMethod.Post, "/user/login") {
                    addHeader("Content-type", "application/json")
                    setBody("""{"username":"crisun","password":"r4e3w2q1"}""")
                }
            ) {
                assertEquals(HttpStatusCode.Created, response.status())

                token = parseToken(response.content.toString())

                println(token)
            }

            with(
                handleRequest(HttpMethod.Post, "/todo") {
                    addHeader("Content-type", "application/json")
                    addHeader("Authorization", "Bearer $token")
                    setBody("""{"id":0,"title":"Tarefa 1","description":"Arrumar a casa"}""")
                }
            ) {
                assertEquals(HttpStatusCode.Created, response.status())

                task = json.decodeFromString(Task.serializer(), response.content.toString())
            }

            with(
                handleRequest(HttpMethod.Delete, "/todo/${task.id}") {
                    addHeader("Content-type", "application/json")
                    addHeader("Authorization", "Bearer $token")
                }
            ) {
                assertEquals(HttpStatusCode.OK, response.status())
            }
        }
    }

    @KtorExperimentalAPI
    @Test(expected = ValidationException::class)
    fun `Try to remove todo and give a error`() {
        var token: String

        withTestApplication(Application::module) {
            with(
                handleRequest(HttpMethod.Post, "/user/login") {
                    addHeader("Content-type", "application/json")
                    setBody("""{"username":"crisun","password":"r4e3w2q1"}""")
                }
            ) {
                assertEquals(HttpStatusCode.Created, response.status())
                token = parseToken(response.content.toString())
            }

            with(
                handleRequest(HttpMethod.Post, "/todo") {
                    addHeader("Content-type", "application/json")
                    addHeader("Authorization", "Bearer $token")
                    setBody("""{"id":0,"title":"Tarefa 1","description":"Arrumar a casa"}""")
                }
            ) {
                assertEquals(HttpStatusCode.Created, response.status())
            }

            with(
                handleRequest(HttpMethod.Delete, "/todo/abc") {
                    addHeader("Content-type", "application/json")
                    addHeader("Authorization", "Bearer $token")
                }
            ) {
                assertEquals(HttpStatusCode.OK, response.status())
            }
        }
    }

    @Test
    @KtorExperimentalAPI
    fun `List one todo with success`() {
        var task: Task
        var token: String

        withTestApplication(Application::module) {
            with(
                handleRequest(HttpMethod.Post, "/user/login") {
                    addHeader("Content-type", "application/json")
                    setBody("""{"username":"crisun","password":"r4e3w2q1"}""")
                }
            ) {
                assertEquals(HttpStatusCode.Created, response.status())

                token = parseToken(response.content.toString())
            }

            with(
                handleRequest(HttpMethod.Post, "/todo") {
                    addHeader("Content-type", "application/json")
                    addHeader("Authorization", "Bearer $token")
                    setBody("""{"id":0,"title":"Tarefa 1","description":"Arrumar a casa"}""")
                }
            ) {
                assertEquals(HttpStatusCode.Created, response.status())

                task = json.decodeFromString(Task.serializer(), response.content.toString())
            }

            with(
                handleRequest(HttpMethod.Get, "/todo/${task.id}") {
                    addHeader("Content-type", "application/json")
                    addHeader("Authorization", "Bearer $token")
                }
            ) {
                assertEquals(HttpStatusCode.OK, response.status())
            }
        }
    }

    @Test
    @KtorExperimentalAPI
    fun `List todos with success`() {
        var token: String

        withTestApplication(Application::module) {
            with(
                handleRequest(HttpMethod.Post, "/user/login") {
                    addHeader("Content-type", "application/json")
                    setBody("""{"username":"crisun","password":"r4e3w2q1"}""")
                }
            ) {
                assertEquals(HttpStatusCode.Created, response.status())

                token = parseToken(response.content.toString())
            }

            with(
                handleRequest(HttpMethod.Get, "/todos") {
                    addHeader("Content-type", "application/json")
                    addHeader("Authorization", "Bearer $token")
                }
            ) {
                assertEquals(HttpStatusCode.OK, response.status())
            }
        }
    }

    @Test(expected = NotFoundException::class)
    @KtorExperimentalAPI
    fun `Try to list a todo and give error`() {
        var token: String

        withTestApplication(Application::module) {
            with(
                handleRequest(HttpMethod.Post, "/user/login") {
                    addHeader("Content-type", "application/json")
                    setBody("""{"username":"crisun","password":"r4e3w2q1"}""")
                }
            ) {
                assertEquals(HttpStatusCode.Created, response.status())

                token = parseToken(response.content.toString())
            }

            with(
                handleRequest(HttpMethod.Get, "/todo/${Int.MAX_VALUE}") {
                    addHeader("Content-type", "application/json")
                    addHeader("Authorization", "Bearer $token")
                }
            ) {
                assertEquals(HttpStatusCode.OK, response.status())
            }
        }
    }

    @Test
    @KtorExperimentalAPI
    fun `List todos with invalid token`() {
        val token = "123"

        withTestApplication(Application::module) {
            with(
                handleRequest(HttpMethod.Get, "/todos") {
                    addHeader("Content-type", "application/json")
                    addHeader("Authorization", "Bearer $token")
                }
            ) {
                assertEquals(HttpStatusCode.Unauthorized, response.status())
            }
        }
    }

    private fun parseToken(content: String) = json.decodeFromString<JsonObject>(content)["token"]?.jsonPrimitive?.content ?: ""
}
