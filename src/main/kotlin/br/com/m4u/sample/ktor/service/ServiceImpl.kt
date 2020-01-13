package br.com.m4u.sample.ktor.service

import br.com.m4u.sample.ktor.repository.Repository

class ServiceImpl(private val repository: Repository) : Service {
    override fun getTitle() = """{"crisun": "${repository.getTitle()}"}"""
}