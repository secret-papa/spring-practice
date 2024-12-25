package hello.springpractice.membership.service

import hello.springpractice.membership.domain.Member
import hello.springpractice.membership.repository.MemberRepository
import hello.springpractice.membership.repository.MemberRepositoryImp
import hello.springpractice.membership.service.dto.MemberDto
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import java.time.LocalDate
import javax.sql.DataSource

@SpringBootTest
class MemberServiceImpTest(
    @Autowired
    private val memberRepository: MemberRepository,
    @Autowired
    private val memberService: MemberService
){
    var member: Member? = null

    @AfterEach
    fun afterEach() {
        if (member != null) {
            memberRepository.deleteById(member!!.id)
            member = null
        }
    }

    @Test
    fun `가입`() {
        val memberDto = MemberDto(
            name = "test",
            email = "test@test.com",
            birthDate = LocalDate.of(1993, 4, 3),
            gender = "MALE",
        )

        member = memberService.register(memberDto)
        assertThat(member).isNotNull()
    }

    @Test
    fun `젋은맴버_가입`() {
        val memberDto = MemberDto(
            name = "test",
            email = "test@test.com",
            birthDate = LocalDate.of(2001, 4, 3),
            gender = "FEMALE",
        )

        assertThatThrownBy { memberService.register(memberDto) }.isInstanceOf(IllegalArgumentException::class.java)
    }


    @TestConfiguration
    class TestConfig(private val dataSource: DataSource) {
        @Bean
        fun memberRepository(): MemberRepository {
            return MemberRepositoryImp(dataSource)
        }

        @Bean
        fun memberService(): MemberService {
            return MemberServiceImp(memberRepository())
        }
    }
}