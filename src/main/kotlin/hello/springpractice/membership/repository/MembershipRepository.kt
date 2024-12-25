package hello.springpractice.membership.repository

import hello.springpractice.domain.Membership

interface MembershipRepository {
    fun save(membership: Membership)
    fun findByName(membership: Membership): Membership?
    fun deleteByName(membership: Membership)
}