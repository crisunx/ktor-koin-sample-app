package br.com.crisun.sample.todo.domain.usecase

import br.com.crisun.sample.todo.domain.repository.TaskRepository

internal class DeleteTaskUseCaseImpl(private val repository: TaskRepository) : DeleteTaskUseCase {
    override fun invoke(id: Long) {
        repository.delete(id)
    }
}
