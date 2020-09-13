package br.com.crisun.sample.todo.service

import br.com.crisun.sample.todo.model.LoginRequest
import br.com.crisun.sample.todo.model.User

interface UserService {
    fun findUser(login: LoginRequest): User
}
