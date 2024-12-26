package hello.springpractice.membership.controller

import hello.springpractice.membership.domain.Membership
import hello.springpractice.membership.repository.MembershipRepository
import hello.springpractice.membership.service.MembershipService
import hello.springpractice.membership.service.dto.MembershipDto
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/apis/membership")
class MembershipController(
    private val membershipRepository: MembershipRepository,
    private val membershipService: MembershipService
) {
    @GetMapping("/")
    fun findAllMembership(): List<Membership> {
        return membershipRepository.findAll()
    }

    @PostMapping("/")
    fun saveMembership(@RequestBody membershipDto: MembershipDto): ResponseEntity<String> {
        membershipService.save(membershipDto)
        return ResponseEntity.ok("ok")
    }
}