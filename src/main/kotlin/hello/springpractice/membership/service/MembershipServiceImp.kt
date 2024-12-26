package hello.springpractice.membership.service

import hello.springpractice.membership.domain.Membership
import hello.springpractice.membership.domain.Partnership
import hello.springpractice.membership.repository.MembershipRepository
import hello.springpractice.membership.service.dto.MembershipDto
import org.springframework.stereotype.Service

@Service
class MembershipServiceImp(
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
}