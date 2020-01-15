package br.com.crisun.sample.ktor.di

import br.com.crisun.sample.ktor.configuration.DatabaseFactory
import br.com.crisun.sample.ktor.repository.MessageRepository
import br.com.crisun.sample.ktor.service.MessageService
import org.koin.dsl.module
import org.koin.experimental.builder.single
import org.koin.experimental.builder.singleBy
import org.springframework.jdbc.core.JdbcTemplate

val appModule = module {
    single<MessageRepository>()
    singleBy<MessageService, MessageService>()
    single { JdbcTemplate(DatabaseFactory.hikari()) }
}