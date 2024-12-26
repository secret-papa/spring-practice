package hello.springpractice.membership.repository

import hello.springpractice.membership.domain.Member
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import javax.sql.DataSource

@SpringBootTest
class MemberRepositoryImpTest(
    @Autowired
    private val memberRepository: MemberRepository
) {
    private val member = Member(name = "test", email = "test@test.com", gender = "MALE")

    @AfterEach
    fun afterEach() {
        memberRepository.deleteById(member.id)
    }

    @Test
    fun `맴버_생성`() {
        memberRepository.save(member)
        val findMember: Member? = memberRepository.findById(member.id)
        assertThat(findMember).isNotNull()
    }

    @Test
    fun `맴버_조회`() {
        memberRepository.save(member)
        val findMember: Member? = memberRepository.findById(member.id)
        assertThat(findMember).isNotNull()
    }

    @Test
    fun `맴버_이름_조회`() {
        memberRepository.save(member)
        val findMember: Member? = memberRepository.findByName(member.name)
        assertThat(findMember).isNotNull()
    }

    @Test
    fun `멤버_조회_없음`() {
        val findMember: Member? = memberRepository.findById("empty")
        assertThat(findMember).isNull()
    }

    @Test
    fun `맴버_전체_조회`() {
        val member1 = Member(name = "test", email = "test1@test.com", gender = "MALE")
        val member2 = Member(name = "test", email = "test2@test.com", gender = "MALE")
        memberRepository.save(member1)
        memberRepository.save(member2)
        val findMembers = memberRepository.findAll()

        assertThat(findMembers).hasSize(2)
        memberRepository.deleteById(member1.id)
        memberRepository.deleteById(member2.id)
    }

    @Test
    fun `맴버_삭제`() {
       memberRepository.save(member)
        val findMember: Member? = memberRepository.findById(member.id)
        assertThat(findMember).isNotNull()

        memberRepository.deleteById(member.id)
        assertThat(memberRepository.findById(member.id)).isNull()
    }


    @TestConfiguration
    class Config(private val dataSource: DataSource) {
        @Bean
        fun memberRepository(): MemberRepository {
            return MemberRepositoryImp(dataSource)
        }
    }
}