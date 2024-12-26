package hello.springpractice.membership.repository

import hello.springpractice.membership.domain.Member
import hello.springpractice.membership.domain.Membership
import hello.springpractice.membership.domain.Partnership
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

    override fun findAll(): List<Member> {
        val sql = "select * from member"
        return template.query(sql, memberRowMapper)
    }

    override fun deleteById(id: String) {
        val sql = "delete from member where id=?"
        template.update(sql, id)
    }

    override fun findSignUpMembership(id: String): List<Membership> {
        val sql = "select ms.code, ms.name from membership ms join member_membership m_ms on ms.code = m_ms.membership join member m on m_ms.member = m.id where m.id=?"
        return template.query(sql, {rs, _ ->
            Membership(
                code = rs.getInt("code"),
                partnership = Partnership.valueOf(rs.getString("name"))
            )
        }, id)
    }
}