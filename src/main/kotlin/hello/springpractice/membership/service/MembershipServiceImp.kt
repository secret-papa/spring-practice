package hello.springpractice.membership.service

import hello.springpractice.membership.domain.Membership
import hello.springpractice.membership.domain.Partnership
import hello.springpractice.membership.repository.MemberRepository
import hello.springpractice.membership.repository.MembershipRepository
import hello.springpractice.membership.service.dto.MembershipDto
import hello.springpractice.membership.service.dto.SignUpForMembershipDto
import org.springframework.stereotype.Service

@Service
class MembershipServiceImp(
    private val memberRepository: MemberRepository,
    private val membershipRepository: MembershipRepository
): MembershipService {
    override fun save(membershipDto: MembershipDto) {
        val code =  when (membershipDto.partnership) {
            Partnership.KAKAO -> 100
            Partnership.NAVER -> 200
            Partnership.COOPANG -> 300
            Partnership.KAKAO_Test -> 400
            Partnership.NAVER_Test -> 500
            Partnership.COOPANG_Test -> 600
        }

        val membership = Membership(code, membershipDto.partnership)
        return membershipRepository.save(membership)
    }

    override fun signUpForMembership(signUpForMembershipDto: SignUpForMembershipDto) {
        val member = memberRepository.findById(signUpForMembershipDto.memberId)
            ?: throw IllegalArgumentException("Not found Member with id: ${signUpForMembershipDto.memberId}")

        val membership = membershipRepository.findByCode(signUpForMembershipDto.membershipCode)
            ?: throw IllegalArgumentException("Not found Membership with code: ${signUpForMembershipDto.membershipCode}")

        membershipRepository.mappingMemberAndMembership(member, membership)
    }
}