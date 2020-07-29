package br.com.crisun.sample.todo.util

import java.math.BigInteger
import java.security.MessageDigest

class Md5Sum(private val md: MessageDigest) {
    fun checksum(source: String): String {
        md.update(source.toByteArray(Charsets.UTF_8))
        return BigInteger(1, md.digest()).toString(16).padStart(32, '0')
    }
}
