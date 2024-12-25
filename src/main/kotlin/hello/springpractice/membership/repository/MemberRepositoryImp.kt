package hello.springpractice.membership.repository

import hello.springpractice.membership.domain.Member
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository
import javax.sql.DataSource

@Repository
class MemberRepositoryImp(dataSource: DataSource): MemberRepository {
    private val template = JdbcTemplate(dataSource)
    private val memberRowMapper = RowMapper { rs, _ -> Member(
        id = rs.getString("id"),
        name = rs.getString("name"),
        email = rs.getString("email"),
        gender = rs.getString("gender"),
        birthDate = rs.getDate("birthDate").toLocalDate()
    )}

    override fun save(member: Member): Member {
        val sql = "insert into member (id, name, email, birthDate, gender) values (?, ?, ?, ?, ?)"
        template.update(sql, member.id, member.name, member.email, member.birthDate, member.gender.name)
        return member
    }

    override fun findById(id: String): Member? {
        try {
            val sql = "select * from member where id=?"
            return template.queryForObject(sql, memberRowMapper, id)
        } catch (error: EmptyResultDataAccessException) {
            return null
        }
    }

    override fun findByName(name: String): Member? {
        try {
            val sql = "select * from member where name=?"
            return template.queryForObject(sql, memberRowMapper, name)
        } catch (error: EmptyResultDataAccessException) {
            return null
        }
    }

    override fun deleteById(id: String) {
        val sql = "delete from member where id=?"
        template.update(sql, id)
    }
}