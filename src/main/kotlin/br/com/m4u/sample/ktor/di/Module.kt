package br.com.m4u.sample.ktor.di

import br.com.m4u.sample.ktor.repository.Repository
import br.com.m4u.sample.ktor.service.Service
import br.com.m4u.sample.ktor.service.ServiceImpl
import org.koin.dsl.module
import org.koin.experimental.builder.single
import org.koin.experimental.builder.singleBy

val appModule = module(createdAtStart = true) {
    single<Repository>()
    singleBy<Service, ServiceImpl>()
}