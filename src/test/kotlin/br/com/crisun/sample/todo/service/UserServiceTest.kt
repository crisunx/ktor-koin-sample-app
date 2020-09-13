package br.com.crisun.sample.todo.service

import br.com.crisun.sample.todo.exception.NotFoundException
import br.com.crisun.sample.todo.model.LoginRequest
import br.com.crisun.sample.todo.model.User
import br.com.crisun.sample.todo.repository.UserRepository
import br.com.crisun.sample.todo.util.Md5Sum
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class UserServiceTest {
    @InjectMockKs
    lateinit var service: UserServiceImpl

    @MockK
    private lateinit var md5: Md5Sum

    @MockK
    private lateinit var repository: UserRepository

    @Before
    fun setUp() = MockKAnnotations.init(this)

    @Test
    fun `Find user in database`() {
        val request = LoginRequest("crisun", "abc123")
        val expected = User(1, "Cristiano", "crisun", "abc123", emptyList())

        every { md5.checksum(request.password) } returns request.password
        every { repository.findUser(request.username, request.password) } returns expected

        val result = service.findUser(request)

        verify(exactly = 1) { repository.findUser(any(), any()) }

        assertEquals(expected, result)
    }

    @Test(expected = NotFoundException::class)
    fun `User not found`() {
        val request = LoginRequest("crisun", "abc123")

        every { md5.checksum(request.password) } returns request.password
        every { repository.findUser(request.username, request.password) } returns null

        service.findUser(request)
    }
}
