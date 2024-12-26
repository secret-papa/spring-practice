package hello.springpractice.membership.service

import hello.springpractice.membership.domain.Membership

interface MembershipService {
    fun save(membership: Membership)
}