package br.com.crisun.sample.todo.configuration

import br.com.crisun.sample.todo.model.User
import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.util.KtorExperimentalAPI
import kotlinx.serialization.json.Json
import java.util.*

@KtorExperimentalAPI
open class AuthConfig(private val json: Json, private val config: Configuration) {
    private val validityInMs = 3_600_000 * 2
    private val algorithm = Algorithm.HMAC256(config.fetchProperty(JWT_SECRET_KEY))

    val verifier: JWTVerifier = JWT.require(algorithm)
        .withIssuer(config.fetchProperty(JWT_ISSUER_KEY))
        .withAudience(config.fetchProperty(JWT_AUDIENCE_KEY))
        .build()

    fun sign(user: User): String = JWT.create()
        .withExpiresAt(expiresAt())
        .withSubject(user.id.toString())
        .withIssuer(config.fetchProperty(JWT_ISSUER_KEY))
        .withAudience(config.fetchProperty(JWT_AUDIENCE_KEY))
        .withClaim("user", json.encodeToString(User.serializer(), user.copy(password = "")))
        .sign(algorithm)

    private fun expiresAt() = Date(System.currentTimeMillis() + validityInMs)

    companion object {
        private const val JWT_SECRET_KEY = "env.jwt.secret"
        private const val JWT_ISSUER_KEY = "env.jwt.issuer"
        private const val JWT_AUDIENCE_KEY = "env.jwt.audience"
    }
}
