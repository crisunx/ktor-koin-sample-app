package br.com.crisun.sample.todo.routes

import br.com.crisun.sample.todo.exception.ValidationException
import br.com.crisun.sample.todo.model.Task
import br.com.crisun.sample.todo.service.TaskService
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.util.pipeline.*
import kotlinx.serialization.ExperimentalSerializationApi
import org.koin.ktor.ext.inject

@ExperimentalSerializationApi
fun Routing.todo() {
    val service by inject<TaskService>()

    authenticate {
        get("/todos") {
            call.respond(service.list())
        }
    }

    authenticate {
        get("/todo/{id}") {
            val id = parseId()

            call.respond(service.findById(id))
        }
    }

    authenticate {
        post("/todo") {
            call.respond(HttpStatusCode.Created, service.insert(call.receive()))
        }
    }

    authenticate {
        put("/todo/{id}") {
            val id = parseId()
            val task = call.receiveOrNull<Task>() ?: throw ValidationException(
                "Task parameter can't be null"
            )

            service.update(id, task).let {
                call.respond(HttpStatusCode.OK)
            }
        }
    }

    authenticate {
        delete("/todo/{id}") {
            val id = parseId()

            service.delete(id).let {
                call.respond(HttpStatusCode.OK)
            }
        }
    }
}

private fun PipelineContext<Unit, ApplicationCall>.parseId(): Long {
    return try {
        call.parameters["id"]?.toLong() ?: throw ValidationException(
            "Id parameter can't be null"
        )
    } catch (e: NumberFormatException) {
        throw ValidationException("Id parameter is invalid")
    }
}
