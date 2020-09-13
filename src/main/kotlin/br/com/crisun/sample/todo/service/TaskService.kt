package br.com.crisun.sample.todo.service

import br.com.crisun.sample.todo.model.Task

interface TaskService {
    fun delete(id: Long)
    fun list(): List<Task>
    fun insert(task: Task): Task
    fun findById(id: Long): Task
    fun update(id: Long, task: Task)
}
