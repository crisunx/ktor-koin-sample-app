package br.com.crisun.sample.todo.domain.repository

import br.com.crisun.sample.todo.domain.model.Task

interface TaskRepository {
    fun list(): List<Task>
    fun delete(id: Long): Int
    fun insert(task: Task): Task
    fun findById(id: Long): Task?
    fun update(id: Long, task: Task): Int
}
