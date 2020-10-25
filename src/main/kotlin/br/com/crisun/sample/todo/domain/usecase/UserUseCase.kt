package br.com.crisun.sample.todo.domain.usecase

import br.com.crisun.sample.todo.domain.model.Login
import br.com.crisun.sample.todo.domain.model.User

interface UserUseCase {
    operator fun invoke(login: Login): User
}
