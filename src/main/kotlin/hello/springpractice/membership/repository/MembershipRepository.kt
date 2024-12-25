package hello.springpractice.membership.repository

import hello.springpractice.membership.domain.Membership

interface MembershipRepository {
    fun save(membership: Membership)
    fun findAll(): List<Membership>
    fun findByName(membership: Membership): Membership?
    fun deleteByName(membership: Membership)
}