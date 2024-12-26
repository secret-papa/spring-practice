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

    override fun mappingMemberAndMembership(member: Member, membership: Membership) {
        val sql = "insert into member_membership (member, membership) values (?, ?)"
        template.update(sql, member.id, membership.code)
    }

    override fun findAllMembershipMember(): HashMap<Partnership, MutableList<Member>> {
        val sql = "select m.id, m.name, m.email, m.gender, m.birthDate, ms.name as partnership from member m join member_membership m_ms on m.id = m_ms.member join membership ms on m_ms.membership = ms.code;"
        val result = HashMap<Partnership, MutableList<Member>>()

        template.query(sql)  { rs, _ ->
            val member = Member(
                id = rs.getString("id"),
                name = rs.getString("name"),
                email = rs.getString("email"),
                gender = rs.getString("gender"),
                birthDate = rs.getDate("birthDate").toLocalDate()
            )

            result.computeIfAbsent(Partnership.valueOf( rs.getString("partnership"))) { mutableListOf() }.add(member)
        }

        return result
    }
}