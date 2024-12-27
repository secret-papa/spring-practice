package hello.springpractice.membership.controller

import hello.springpractice.membership.domain.Member
import hello.springpractice.membership.domain.Membership
import hello.springpractice.membership.domain.Order
import hello.springpractice.membership.repository.MemberRepository
import hello.springpractice.membership.repository.OrderRepository
import hello.springpractice.membership.service.MemberService
import hello.springpractice.membership.service.dto.MemberDto
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/apis/members")
class MemberController(
    private val orderRepository: OrderRepository,
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

    @GetMapping("/{id}/membership")
    fun findSignUpMembership(@PathVariable id: String): List<Membership> {
        return memberRepository.findSignUpMembership(id)
    }

    @GetMapping("/{id}/orders")
    fun findOrders(@PathVariable id: String): List<Order> {
        return orderRepository.findByMemberId(id)
    }

    @PostMapping("/")
    fun joinMember(@RequestBody memberDto: MemberDto): Member {
        return memberService.register(memberDto)
    }

}