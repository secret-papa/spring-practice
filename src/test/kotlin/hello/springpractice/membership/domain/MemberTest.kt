package hello.springpractice.membership.domain

import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.LocalDate

class MemberTest {
    var member: Member = Member(name = "Lee Seungh Ha", email = "email@gmail.com", gender = "MALE")

    @Test
    fun `아이디_조회`() {
        assertThat(member.id).isNotNull()
    }

    @Test
    fun `이름_조회`() {
        assertThat(member.name).isEqualTo("Lee Seungh Ha")
    }

    @Test
    fun `이메일_조회`() {
        assertThat(member.email).isEqualTo("email@gmail.com")
    }

    @Test
    fun `성별_조회`() {
        assertThat(member.gender.name).isEqualTo("MALE")
    }

    @Test
    fun `잘못된_성별`() {
        assertThatThrownBy { Member(name = "Lee Seungh Ha", email = "email@gmail.com", gender = "MALE_FEMALE") }.isInstanceOf(IllegalArgumentException::class.java)
    }

    @Test
    fun `생년월일_조회`() {
        assertThat(member.birthDate).isInstanceOf(LocalDate::class.java)
    }
}