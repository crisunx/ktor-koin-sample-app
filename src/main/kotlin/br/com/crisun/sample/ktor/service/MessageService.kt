package br.com.crisun.sample.ktor.service

import br.com.crisun.sample.ktor.model.Message
import br.com.crisun.sample.ktor.repository.MessageRepository

class MessageService(private val repository: MessageRepository) {
    fun findAll(): List<Message> {
        return repository.findAll()
    }

    fun findById(id: String): Message? {
        return repository.findById(id.toInt())
    }

    fun insert(message: Message?) {
        message?.let { repository.insert(it) }
    }

    fun update(id: Int, message: Message) {
        repository.update(id, message)
    }

    fun delete(id: Int) {
        repository.delete(id)
    }
}