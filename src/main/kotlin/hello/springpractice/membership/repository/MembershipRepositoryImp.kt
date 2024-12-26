package hello.springpractice.membership.repository

import hello.springpractice.membership.domain.Membership
import hello.springpractice.membership.domain.Partnership
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository
import javax.sql.DataSource

@Repository
class MembershipRepositoryImp(dataSource: DataSource): MembershipRepository {
    private val template = JdbcTemplate(dataSource)
    private val membershipRowMapper = RowMapper<Membership> {
        rs, _ -> Membership(
            rs.getInt("code"),
            Partnership.valueOf(rs.getString("name"))
        )
    }

    override fun save(membership: Membership) {
        val sql = "insert into membership(code, name) values (?, ?)"
        template.update(sql, membership.code, membership.partnership.name)
    }

    override fun findAll(): List<Membership> {
        val sql = "select * from membership"
        return template.query(sql, membershipRowMapper)
    }

    override fun findByPartnership(partnership: Partnership): Membership? {
        try {
            val sql = "select * from membership where name=?"
            return template.queryForObject(sql, membershipRowMapper, partnership.name)
        } catch(error: EmptyResultDataAccessException) {
           return null
        }
    }

    override fun findByCode(code: Number): Membership? {
        try {
            val sql = "select * from membership where code = ?"
            return template.queryForObject(sql, membershipRowMapper, code)
        } catch (error: EmptyResultDataAccessException) {
            return null
        }
    }

    override fun deleteByCode(code: Number) {
        val sql = "delete from membership where code = ?"
        template.update(sql, code)
    }
}