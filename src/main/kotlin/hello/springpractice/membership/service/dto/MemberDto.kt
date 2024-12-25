package hello.springpractice.membership.service.dto

import java.time.LocalDate

data class MemberDto(
    val name: String,
    val email: String,
    val gender: String,
    val birthDate: LocalDate?
){}
