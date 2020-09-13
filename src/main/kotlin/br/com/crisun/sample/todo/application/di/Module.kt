package br.com.crisun.sample.todo.application.di

import br.com.crisun.sample.todo.application.configuration.AuthConfig
import br.com.crisun.sample.todo.application.configuration.Configuration
import br.com.crisun.sample.todo.application.configuration.DatabaseConfig
import br.com.crisun.sample.todo.domain.util.Md5Sum
import io.ktor.util.*
import kotlinx.serialization.json.Json
import org.koin.dsl.module
import org.koin.experimental.builder.single
import org.springframework.jdbc.core.JdbcTemplate
import java.security.MessageDigest
import javax.sql.DataSource

@KtorExperimentalAPI
val appModule = module {
    single<AuthConfig>()
    single<Configuration>()

    single { JdbcTemplate(get()) }
    single<DataSource> { DatabaseConfig(get()).dataSource() }
    factory { Md5Sum(MessageDigest.getInstance("MD5")) }
}

val jsonModule = module {
    single {
        Json {
            ignoreUnknownKeys = true
        }
    }
}
