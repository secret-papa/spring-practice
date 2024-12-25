package hello.springpractice.membership.domain

import java.time.LocalDate
import java.util.UUID

class Member(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val email: String,
    val birthDate: LocalDate = LocalDate.now(),
    gender: String
    ) {
    val gender: Gender = Gender.fromString(gender)
}

enum class Gender(val value: String) {
    MALE("MALE"),
    FEMALE("FEMALE");

    companion object {
        fun fromString(value: String): Gender {
            return when (value) {
                "MALE" -> MALE
                "FEMALE" -> FEMALE
                else -> throw IllegalArgumentException("Invalid gender: $value")
            }
        }
    }
}