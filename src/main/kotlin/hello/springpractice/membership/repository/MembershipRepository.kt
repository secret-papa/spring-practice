package hello.springpractice.membership.repository

import hello.springpractice.membership.domain.Member
import hello.springpractice.membership.domain.Membership
import hello.springpractice.membership.domain.Partnership

interface MembershipRepository {
    fun save(membership: Membership)
    fun findAll(): List<Membership>
    fun findByPartnership(partnership: Partnership): Membership?
    fun findByCode(code: Number): Membership?
    fun deleteByCode(code: Number)
    fun mappingMemberAndMembership(member: Member, membership: Membership)
}