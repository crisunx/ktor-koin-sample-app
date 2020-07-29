package br.com.crisun.sample.todo.repository

import br.com.crisun.sample.todo.model.User
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper

class UserRepository(private val jdbcTemplate: JdbcTemplate) {
    private val SELECT_ROLES = "SELECT r.role FROM roles r INNER JOIN user_roles ur ON r.id = ur.role_id AND ur.user_id = ?;"
    private val SELECT = "SELECT id, name, username, password, create_date, active FROM users WHERE username = ? and password = ?"

    private val mapper: RowMapper<User> = RowMapper { rs, _ ->
        User(
            id = rs.getInt("id"),
            name = rs.getString("name"),
            username = rs.getString("username"),
            password = rs.getString("password"),
            roles = roles(rs.getInt("id"))
        )
    }

    fun findUser(username: String, password: String): User? {
        return jdbcTemplate.query(SELECT, mapper, username, password).getOrNull(0)
    }

    private fun roles(id: Int): List<String> {
        return jdbcTemplate.queryForList(SELECT_ROLES, String::class.java, id)
    }
}
