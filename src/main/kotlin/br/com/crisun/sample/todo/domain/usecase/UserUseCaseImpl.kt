package br.com.crisun.sample.todo.domain.usecase

import br.com.crisun.sample.todo.application.exception.NotFoundException
import br.com.crisun.sample.todo.domain.model.Login
import br.com.crisun.sample.todo.domain.model.User
import br.com.crisun.sample.todo.domain.repository.UserRepository
import br.com.crisun.sample.todo.domain.util.Md5Sum

internal class UserUseCaseImpl(private val md5: Md5Sum, private val repository: UserRepository) : UserUseCase {
    override fun findUser(login: Login): User {
        return repository.findUser(login.username, md5.checksum(login.password)) ?: throw NotFoundException(
            "User not found"
        )
    }
}
