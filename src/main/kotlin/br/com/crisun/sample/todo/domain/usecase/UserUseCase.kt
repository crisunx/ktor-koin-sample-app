package br.com.crisun.sample.todo.domain.usecase

import br.com.crisun.sample.todo.domain.model.LoginRequest
import br.com.crisun.sample.todo.domain.model.User

interface UserUseCase {
    fun findUser(login: LoginRequest): User
}
