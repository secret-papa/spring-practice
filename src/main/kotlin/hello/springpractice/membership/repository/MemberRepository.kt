package hello.springpractice.membership.repository

import hello.springpractice.membership.domain.Member

interface MemberRepository {
    fun save(member: Member): Member
    fun findById(id: String): Member?
    fun findByName(name: String): Member?
    fun findAll(): List<Member>
    fun deleteById(id: String)
}