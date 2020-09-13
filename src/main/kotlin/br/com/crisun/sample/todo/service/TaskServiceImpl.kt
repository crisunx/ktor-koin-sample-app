package br.com.crisun.sample.todo.service

import br.com.crisun.sample.todo.exception.NotFoundException
import br.com.crisun.sample.todo.model.Task
import br.com.crisun.sample.todo.repository.TaskRepository

class TaskServiceImpl(private val repository: TaskRepository) : TaskService {
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
