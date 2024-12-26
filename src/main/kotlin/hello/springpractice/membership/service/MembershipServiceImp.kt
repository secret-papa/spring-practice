package hello.springpractice.membership.service

import hello.springpractice.membership.domain.Membership
import hello.springpractice.membership.repository.MembershipRepository

class MembershipServiceImp(
    private val membershipRepository: MembershipRepository
): MembershipService {
    override fun save(membership: Membership) {
        return membershipRepository.save(membership)
    }
}