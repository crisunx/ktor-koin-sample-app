package br.com.crisun.sample.ktor.configuration

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.application.ApplicationEnvironment
import io.ktor.util.KtorExperimentalAPI

@KtorExperimentalAPI
class DatabaseConfiguration(private val env: ApplicationEnvironment) {
    fun dataSource(): HikariDataSource {
        return HikariDataSource(hikariConfig())
    }

    private fun hikariConfig(): HikariConfig {
        return HikariConfig().apply {
            isAutoCommit = true
            jdbcUrl = env.config.property("ktor.datasource.jdbcUrl").getString()
            minimumIdle = env.config.property("ktor.datasource.minIdle").getString().toInt()
            driverClassName = env.config.property("ktor.datasource.driverClassName").getString()
            maximumPoolSize = env.config.property("ktor.datasource.maxPoolSize").getString().toInt()
            connectionTestQuery = env.config.property("ktor.datasource.connectionTestQuery").getString()
            transactionIsolation = "TRANSACTION_REPEATABLE_READ"
            validate()
        }
    }
}
