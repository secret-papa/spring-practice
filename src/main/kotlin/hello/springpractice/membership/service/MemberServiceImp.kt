package hello.springpractice.membership.service

import hello.springpractice.membership.domain.Member
import hello.springpractice.membership.repository.MemberRepository
import hello.springpractice.membership.service.dto.MemberDto
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class MemberServiceImp(
    val memberRepository: MemberRepository
): MemberService {
    override fun register(memberDto: MemberDto): Member {
        val member = Member(
            name = memberDto.name,
            email = memberDto.email,
            gender = memberDto.gender,
            birthDate = memberDto.birthDate ?: LocalDate.now()
        )

        if (member.birthDate.year >= 2000) {
            throw IllegalArgumentException("register only older than 2000 year")
        }

        return memberRepository.save(member)
    }
}