package br.com.crisun.sample.todo.domain.usecase

interface DeleteTaskUseCase {
    operator fun invoke(id: Long)
}
