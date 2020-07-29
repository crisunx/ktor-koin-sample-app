package br.com.crisun.sample.todo.repository

import br.com.crisun.sample.todo.model.Status
import br.com.crisun.sample.todo.model.Task
import org.springframework.dao.DataAccessResourceFailureException
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.jdbc.support.KeyHolder
import java.sql.Statement

class TaskRepository(private val jdbcTemplate: JdbcTemplate) {
    private val SELECT = "SELECT id, title, description, create_date, start_date, finish_date, status FROM task"
    private val INSERT = "INSERT INTO task (title, description) VALUES (?, ?)"
    private val DELETE = "DELETE FROM task WHERE id = ?"
    private val UPDATE = "UPDATE task SET title = ?, description = ?, status = ? WHERE id = ?"
    private val SELECT_BY_ID = "SELECT id, title, description, create_date, start_date, finish_date, status FROM task WHERE id = ?"

    private val mapper: RowMapper<Task> = RowMapper { rs, _ ->
        Task(
            id = rs.getLong("id"),
            title = rs.getString("title"),
            description = rs.getString("description"),
            createDate = rs.getTimestamp("create_date").toLocalDateTime(),
            startDate = rs.getTimestamp("start_date")?.toLocalDateTime(),
            finishDate = rs.getTimestamp("finish_date")?.toLocalDateTime(),
            status = Status.valueOf(rs.getString("status"))
        )
    }

    fun list(): List<Task> {
        return jdbcTemplate.query(SELECT, mapper)
    }

    fun insert(task: Task): Task {
        val holder: KeyHolder = GeneratedKeyHolder()

        jdbcTemplate.update(
            { con ->
                val ps = con.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)
                ps.setString(1, task.title)
                ps.setString(2, task.description)
                ps
            },
            holder
        )

        if (holder.keys == null) {
            throw DataAccessResourceFailureException("Insert task error")
        }

        return task.copy(id = holder.keys?.get("id") as Long)
    }

    fun delete(id: Long): Int {
        return jdbcTemplate.update(DELETE, id)
    }

    fun update(id: Long, task: Task): Int {
        return jdbcTemplate.update(UPDATE, task.title, task.description, task.status.name, id)
    }

    fun findById(id: Long): Task? {
        return jdbcTemplate.query(SELECT_BY_ID, mapper, id).getOrNull(0)
    }
}
