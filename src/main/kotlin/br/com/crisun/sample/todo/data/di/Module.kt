package br.com.crisun.sample.todo.data.di

import br.com.crisun.sample.todo.data.repository.TaskRepositoryImpl
import br.com.crisun.sample.todo.data.repository.UserRepositoryImpl
import br.com.crisun.sample.todo.domain.repository.TaskRepository
import br.com.crisun.sample.todo.domain.repository.UserRepository
import org.koin.dsl.module
import org.koin.experimental.builder.factoryBy

val dataModule = module {
    factoryBy<TaskRepository, TaskRepositoryImpl>()
    factoryBy<UserRepository, UserRepositoryImpl>()
}
