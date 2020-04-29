package br.com.crisun.sample.ktor.di

import br.com.crisun.sample.ktor.configuration.DatabaseConfiguration
import br.com.crisun.sample.ktor.repository.MessageRepository
import br.com.crisun.sample.ktor.service.MessageService
import br.com.crisun.sample.ktor.util.DatabaseCheck
import io.ktor.util.KtorExperimentalAPI
import javax.sql.DataSource
import org.koin.dsl.module
import org.koin.experimental.builder.single
import org.springframework.jdbc.core.JdbcTemplate

@KtorExperimentalAPI
val appModule = module {
    single<DatabaseCheck>()
    single<MessageService>()
    single<MessageRepository>()
    single { JdbcTemplate(get()) }
    single<DataSource> { DatabaseConfiguration(get()).dataSource() }
}