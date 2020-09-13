package br.com.crisun.sample.todo.domain.usecase

import br.com.crisun.sample.todo.application.exception.NotFoundException
import br.com.crisun.sample.todo.application.util.Md5Sum
import br.com.crisun.sample.todo.domain.model.LoginRequest
import br.com.crisun.sample.todo.domain.model.User
import br.com.crisun.sample.todo.domain.repository.UserRepository

internal class UserUseCaseImpl(private val md5: Md5Sum, private val repository: UserRepository) : UserUseCase {
    override fun findUser(login: LoginRequest): User {
        return repository.findUser(login.username, md5.checksum(login.password)) ?: throw NotFoundException(
            "User not found"
        )
    }
}
