package hello.springpractice.membership.service

import hello.springpractice.membership.domain.Member
import hello.springpractice.membership.service.dto.MemberDto

interface MemberService {
    fun register(memberDto: MemberDto): Member
}