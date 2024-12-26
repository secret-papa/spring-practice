package hello.springpractice.membership.controller

import hello.springpractice.membership.domain.Membership
import hello.springpractice.membership.repository.MembershipRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/apis/membership")
class MembershipController(
    private val membershipRepository: MembershipRepository
) {
    @GetMapping("/")
    fun findAllMembership(): List<Membership> {
        return membershipRepository.findAll()
    }

    @PostMapping("/")
    fun saveMembership(@RequestBody membership: Membership): ResponseEntity<String> {
        membershipRepository.save(membership)

        return ResponseEntity.ok("ok")
    }
}