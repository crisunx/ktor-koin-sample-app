package br.com.crisun.sample.todo.repository

import br.com.crisun.sample.todo.model.Task

interface TaskRepository {
    fun list(): List<Task>
    fun delete(id: Long): Int
    fun insert(task: Task): Task
    fun findById(id: Long): Task?
    fun update(id: Long, task: Task): Int
}
