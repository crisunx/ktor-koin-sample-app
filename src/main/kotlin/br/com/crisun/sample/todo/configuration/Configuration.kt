package br.com.crisun.sample.todo.configuration

import com.typesafe.config.ConfigFactory
import io.ktor.config.HoconApplicationConfig
import io.ktor.util.KtorExperimentalAPI

@KtorExperimentalAPI
class Configuration {
    private val config by lazy {
        HoconApplicationConfig(ConfigFactory.load())
    }

    fun fetchProperty(key: String) = config.property(key).getString()
}
