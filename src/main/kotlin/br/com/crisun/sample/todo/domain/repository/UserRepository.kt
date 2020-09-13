package br.com.crisun.sample.todo.domain.repository

import br.com.crisun.sample.todo.domain.model.User

interface UserRepository {
    fun findUser(username: String, password: String): User?
}
