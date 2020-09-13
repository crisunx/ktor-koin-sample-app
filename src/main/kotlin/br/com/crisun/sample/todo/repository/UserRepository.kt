package br.com.crisun.sample.todo.repository

import br.com.crisun.sample.todo.model.User

interface UserRepository {
    fun findUser(username: String, password: String): User?
}
