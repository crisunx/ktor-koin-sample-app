package br.com.crisun.sample.todo.service

import br.com.crisun.sample.todo.exception.NotFoundException
import br.com.crisun.sample.todo.model.Task
import br.com.crisun.sample.todo.repository.TaskRepository

class TaskService(private val repository: TaskRepository) {
    fun list(): List<Task> {
        return repository.list()
    }

    fun insert(task: Task): Task {
        return repository.insert(task)
    }

    fun update(id: Long, task: Task) {
        if (repository.update(id, task) == 0) {
            throw NotFoundException("Task not found")
        }
    }

    fun delete(id: Long) {
        repository.delete(id)
    }

    fun findById(id: Long): Task {
        return repository.findById(id) ?: throw NotFoundException(
            "Task not found"
        )
    }
}
