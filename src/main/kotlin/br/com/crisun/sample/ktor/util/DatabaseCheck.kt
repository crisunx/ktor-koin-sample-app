package br.com.crisun.sample.ktor.util

import io.ktor.application.ApplicationEnvironment
import io.ktor.util.KtorExperimentalAPI
import org.springframework.dao.support.DataAccessUtils
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.SingleColumnRowMapper

@KtorExperimentalAPI
class DatabaseCheck(private val jdbcTemplate: JdbcTemplate, private val env: ApplicationEnvironment) : HealthIndicator() {
    override suspend fun doHealthCheck(): Status {
        try {
            val validationQuery = env.config.property("ktor.datasource.connectionTestQuery").getString()

            jdbcTemplate.query(validationQuery, SingleColumnRowMapper<Any?>()).also {
                DataAccessUtils.requiredSingleResult<Any>(it)
                return Status.UP
            }
        } catch (e: Exception) {
            return Status.DOWN
        }
    }
}