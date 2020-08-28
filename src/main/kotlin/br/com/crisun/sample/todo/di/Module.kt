package br.com.crisun.sample.todo.di

import br.com.crisun.sample.todo.configuration.AuthConfig
import br.com.crisun.sample.todo.configuration.Configuration
import br.com.crisun.sample.todo.configuration.DatabaseConfig
import br.com.crisun.sample.todo.repository.TaskRepository
import br.com.crisun.sample.todo.repository.UserRepository
import br.com.crisun.sample.todo.service.TaskService
import br.com.crisun.sample.todo.service.UserService
import br.com.crisun.sample.todo.util.Md5Sum
import io.ktor.util.KtorExperimentalAPI
import kotlinx.serialization.json.Json
import org.koin.dsl.module
import org.koin.experimental.builder.single
import org.springframework.jdbc.core.JdbcTemplate
import java.security.MessageDigest
import javax.sql.DataSource

@KtorExperimentalAPI
val appModule = module {
    single<TaskService>()
    single<UserService>()
    single<Configuration>()
    single<TaskRepository>()
    single<UserRepository>()

    single { JdbcTemplate(get()) }
    single<DataSource> { DatabaseConfig(get()).dataSource() }
    single { Md5Sum(MessageDigest.getInstance("MD5")) }
}

@KtorExperimentalAPI
val authModule = module {
    single<AuthConfig>()
}

val jsonModule = module {
    single {
        Json {
            ignoreUnknownKeys = true
        }
    }
}
