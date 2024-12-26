package hello.springpractice.membership.controller

import hello.springpractice.membership.domain.Member
import hello.springpractice.membership.domain.Membership
import hello.springpractice.membership.domain.Partnership
import hello.springpractice.membership.repository.MembershipRepository
import hello.springpractice.membership.service.MembershipService
import hello.springpractice.membership.service.dto.MembershipDto
import hello.springpractice.membership.service.dto.SignUpForMembershipDto
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


    @GetMapping("/members")
    fun findAllSignUpMember(): HashMap<Partnership, MutableList<Member>> {
       return membershipRepository.findAllMembershipMember()
    }

    @PostMapping("/")
    fun saveMembership(@RequestBody membershipDto: MembershipDto): ResponseEntity<String> {
        membershipService.save(membershipDto)
        return ResponseEntity.ok("ok")
    }

    @PostMapping("/sign-up")
    fun signUpMembership(@RequestBody signupForMembershipDto: SignUpForMembershipDto): ResponseEntity<String> {
        membershipService.signUpForMembership(signupForMembershipDto)
        return ResponseEntity.ok("ok")
    }
}