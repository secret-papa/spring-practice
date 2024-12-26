package hello.springpractice.membership.controller

import hello.springpractice.membership.domain.Member
import hello.springpractice.membership.repository.MemberRepository
import hello.springpractice.membership.service.MemberService
import hello.springpractice.membership.service.dto.MemberDto
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/apis/members")
class MemberController(
    private val memberService: MemberService,
    private val memberRepository: MemberRepository
) {
    @GetMapping
    fun findAll():List<Member> {
        return memberRepository.findAll()
    }

    @GetMapping("/{id}")
    fun findMember(@PathVariable id: String): Member? {
        return memberRepository.findById(id)
    }

    @PostMapping("/")
    fun joinMember(@RequestBody memberDto: MemberDto): Member {
        return memberService.register(memberDto)
    }
}