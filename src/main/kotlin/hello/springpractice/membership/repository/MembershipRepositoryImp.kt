package hello.springpractice.membership.repository

import hello.springpractice.domain.Membership
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository
import javax.sql.DataSource

@Repository
class MembershipRepositoryImp(dataSource: DataSource): MembershipRepository {
    private val template = JdbcTemplate(dataSource)
    private val membershipRowMapper = RowMapper<Membership> { rs, _ -> Membership.valueOf(rs.getString("name")) }

    override fun save(membership: Membership) {
        val sql = "insert into membership(name) values (?)"
        template.update(sql, membership.name)
    }

    override fun findByName(membership: Membership): Membership? {
        try {
            val sql = "select * from membership where name=?"
            return template.queryForObject(sql, membershipRowMapper, membership.name)
        } catch(error: EmptyResultDataAccessException) {
           return null
        }
    }

    override fun deleteByName(membership: Membership) {
        val sql = "delete from membership where name=?"
        template.update(sql, membership.name)
    }
}