package br.com.crisun.sample.todo.data.di

import br.com.crisun.sample.todo.data.repository.TaskRepositoryImpl
import br.com.crisun.sample.todo.data.repository.UserRepositoryImpl
import br.com.crisun.sample.todo.domain.repository.TaskRepository
import br.com.crisun.sample.todo.domain.repository.UserRepository
import org.koin.dsl.module
import org.koin.experimental.builder.singleBy

val dataModule = module {
    singleBy<TaskRepository, TaskRepositoryImpl>()
    singleBy<UserRepository, UserRepositoryImpl>()
}
