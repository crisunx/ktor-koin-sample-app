package br.com.crisun.sample.todo.domain.usecase

import br.com.crisun.sample.todo.domain.model.Task
import br.com.crisun.sample.todo.domain.repository.TaskRepository
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

internal class TaskUseCaseTest {

    @InjectMockKs
    lateinit var useCase: TaskUseCaseImpl

    @MockK
    private lateinit var repository: TaskRepository

    @Before
    fun setUp() = MockKAnnotations.init(this)

    @Test
    fun `List all tasks from database`() {
        val expected = listOf(
            Task(1, "title1", "description1"),
            Task(2, "title2", "description2")
        )

        every { repository.list() } returns expected

        val result = useCase.list()

        verify(exactly = 1) { repository.list() }
        assertEquals(expected.size, result.size)
    }

    @Test
    fun `Find task by id`() {
        val taskId = 1L
        val expected = Task(taskId, "title1", "description1")

        every { repository.findById(taskId) } returns expected

        val result = useCase.findById(taskId)

        verify(exactly = 1) { repository.findById(taskId) }

        assertEquals(expected, result)
    }
}
