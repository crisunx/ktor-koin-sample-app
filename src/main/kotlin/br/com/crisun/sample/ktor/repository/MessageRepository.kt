package br.com.crisun.sample.ktor.repository

import br.com.crisun.sample.ktor.model.Message
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper

class MessageRepository(val jdbcTemplate: JdbcTemplate) {
    private val ticketMapper: RowMapper<Message> = RowMapper { rs, _ ->
        Message(
            id = rs.getInt("id"),
            text = rs.getString("text")
        )
    }

    fun getMessages() : List<Message> {
        return jdbcTemplate.query("SELECT * FROM message", ticketMapper)
    }
}