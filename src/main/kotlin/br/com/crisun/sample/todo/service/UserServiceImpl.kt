package br.com.crisun.sample.todo.service

import br.com.crisun.sample.todo.exception.NotFoundException
import br.com.crisun.sample.todo.model.LoginRequest
import br.com.crisun.sample.todo.model.User
import br.com.crisun.sample.todo.repository.UserRepository
import br.com.crisun.sample.todo.util.Md5Sum

class UserServiceImpl(private val md5: Md5Sum, private val repository: UserRepository) : UserService {
    override fun findUser(login: LoginRequest): User {
        return repository.findUser(login.username, md5.checksum(login.password)) ?: throw NotFoundException(
            "User not found"
        )
    }
}
