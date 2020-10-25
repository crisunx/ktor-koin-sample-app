package br.com.crisun.sample.todo.domain.di

import br.com.crisun.sample.todo.domain.usecase.*
import br.com.crisun.sample.todo.domain.usecase.TaskUseCaseImpl
import br.com.crisun.sample.todo.domain.usecase.UserUseCaseImpl
import org.koin.dsl.module
import org.koin.experimental.builder.factoryBy

val domainModule = module {
    factoryBy<TaskUseCase, TaskUseCaseImpl>()
    factoryBy<UserUseCase, UserUseCaseImpl>()
    factoryBy<DeleteTaskUseCase, DeleteTaskUseCaseImpl>()
}
