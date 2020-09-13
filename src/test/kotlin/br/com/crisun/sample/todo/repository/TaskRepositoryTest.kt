package br.com.crisun.sample.todo.repository

import br.com.crisun.sample.todo.model.Task
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper

class TaskRepositoryTest {
    @MockK
    lateinit var jdbcTemplate: JdbcTemplate

    @InjectMockKs
    private lateinit var repository: TaskRepositoryImpl

    @Before
    fun setUp() = MockKAnnotations.init(this)

    @Test
    fun `List all tasks from database`() {
        val expected = listOf(
            Task(1, "title1", "description1"),
            Task(2, "title2", "description2"),
            Task(3, "title3", "description3")
        )

        every { jdbcTemplate.query(ofType(String::class), ofType(RowMapper::class)) } returns expected

        val result = repository.list()

        assertEquals(expected.size, result.size)
    }

    @Test
    fun `List one task from database`() {
        val taskId = 1L
        val expected = Task(taskId, "title1", "description1")

        every { jdbcTemplate.query(ofType(String::class), ofType(RowMapper::class), taskId) } returns listOf(expected)

        val result = repository.findById(taskId)

        assertEquals(expected, result)
    }
}
