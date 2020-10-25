package br.com.crisun.sample.todo.domain.usecase

import br.com.crisun.sample.todo.application.exception.NotFoundException
import br.com.crisun.sample.todo.domain.repository.UserRepository
import br.com.crisun.sample.todo.domain.util.Md5Sum
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

internal class UserUseCaseTest {
    @InjectMockKs
    lateinit var useCase: UserUseCaseImpl

    @MockK
    private lateinit var md5: Md5Sum

    @MockK
    private lateinit var repository: UserRepository

    @Before
    fun setUp() = MockKAnnotations.init(this)

    @Test
    fun `Find user in database`() {
        val request = Login("crisun", "abc123")
        val expected = User(1, "Cristiano", "crisun", "abc123", emptyList())

        every { md5.checksum(request.password) } returns request.password
        every { repository.findUser(request.username, request.password) } returns expected

        val result = useCase(request)

        verify(exactly = 1) { repository.findUser(any(), any()) }

        assertEquals(expected, result)
    }

    @Test(expected = NotFoundException::class)
    fun `User not found`() {
        val request = Login("crisun", "abc123")

        every { md5.checksum(request.password) } returns request.password
        every { repository.findUser(request.username, request.password) } returns null

        useCase(request)
    }
}
