package hello.springpractice.membership.service

import hello.springpractice.membership.service.dto.MembershipDto

interface MembershipService {
    fun save(membershipDto: MembershipDto)
}