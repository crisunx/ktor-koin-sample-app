package br.com.crisun.sample.todo.domain.usecase

import br.com.crisun.sample.todo.domain.model.Task

interface TaskUseCase {
    fun delete(id: Long)
    fun list(): List<Task>
    fun insert(task: Task): Task
    fun findById(id: Long): Task
    fun update(id: Long, task: Task)
}
