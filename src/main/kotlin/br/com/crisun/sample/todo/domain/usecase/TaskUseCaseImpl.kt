package br.com.crisun.sample.todo.domain.usecase

import br.com.crisun.sample.todo.application.exception.NotFoundException
import br.com.crisun.sample.todo.domain.model.Task
import br.com.crisun.sample.todo.domain.repository.TaskRepository

internal class TaskUseCaseImpl(private val repository: TaskRepository) : TaskUseCase {
    override fun list(): List<Task> {
        return repository.list()
    }

    override fun insert(task: Task): Task {
        return repository.insert(task)
    }

    override fun update(id: Long, task: Task) {
        if (repository.update(id, task) == 0) {
            throw NotFoundException("Task not found")
        }
    }

    override fun delete(id: Long) {
        repository.delete(id)
    }

    override fun findById(id: Long): Task {
        return repository.findById(id) ?: throw NotFoundException(
            "Task not found"
        )
    }
}
