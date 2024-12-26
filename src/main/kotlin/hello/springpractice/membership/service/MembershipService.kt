package hello.springpractice.membership.service

import hello.springpractice.membership.service.dto.MembershipDto
import hello.springpractice.membership.service.dto.SignUpForMembershipDto

interface MembershipService {
    fun save(membershipDto: MembershipDto)
    fun signUpForMembership(signUpForMembershipDto: SignUpForMembershipDto)
}