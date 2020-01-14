package br.com.crisun.sample.ktor.service

import br.com.crisun.sample.ktor.model.Message

interface MessageService {
    fun getMessages(): List<Message>
}
