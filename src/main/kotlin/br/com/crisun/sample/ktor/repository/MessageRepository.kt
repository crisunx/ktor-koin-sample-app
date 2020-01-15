package br.com.crisun.sample.ktor.repository

import br.com.crisun.sample.ktor.model.Message
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper

class MessageRepository(val jdbcTemplate: JdbcTemplate) {
    private val SELECT = "SELECT id, text FROM message"
    private val DELETE = "DELETE FROM message WHERE id = ?"
    private val INSERT = "INSERT INTO message (text) VALUES (?)"
    private val UPDATE = "UPDATE message SET text = ? WHERE id = ?"
    private val SELECT_BY_ID = "SELECT id, text FROM message WHERE id = ?"

    private val mapper: RowMapper<Message> = RowMapper { rs, _ ->
        Message(
            id = rs.getInt("id"),
            text = rs.getString("text")
        )
    }

    fun insert(message: Message): Int {
        return jdbcTemplate.update(INSERT, message.text)
    }

    fun delete(id: Int): Int {
        return jdbcTemplate.update(DELETE, id)
    }

    fun update(id: Int, message: Message): Int {
        return jdbcTemplate.update(UPDATE, message.text, id)
    }

    fun findAll(): List<Message> {
        return jdbcTemplate.query(SELECT, mapper)
    }

    fun findById(id: Int): Message? {
        return jdbcTemplate.query(SELECT_BY_ID, mapper, id).getOrNull(0)
    }
}