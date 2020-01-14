package br.com.crisun.sample.ktor.service

import br.com.crisun.sample.ktor.model.Message
import br.com.crisun.sample.ktor.repository.MessageRepository

class MessageServiceImpl(private val repository: MessageRepository) : MessageService {
    override fun getMessages() : List<Message> {
        return repository.getMessages()
    }
}