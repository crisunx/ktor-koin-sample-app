package br.com.crisun.sample.todo.application.util

import org.junit.Before
import org.junit.Test
import java.security.MessageDigest
import kotlin.test.assertEquals

class Md5SumTest {

    private lateinit var md5: Md5Sum

    @Before
    fun setUp() {
        md5 = Md5Sum(MessageDigest.getInstance("MD5"))
    }

    @Test
    fun `Validate checksum`() {
        val expected = "3deb282f85a0a0d1ae7c90f4dcd64218"

        val checksum = md5.checksum("crisun")

        assertEquals(expected, checksum)
    }
}
