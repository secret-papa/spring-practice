package hello.springpractice.membership.repository

import hello.springpractice.membership.domain.Membership
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.dao.DuplicateKeyException
import javax.sql.DataSource

@SpringBootTest
class MembershipRepositoryImpTest(
    @Autowired
    private val membershipRepository: MembershipRepository
) {

    @AfterEach
    fun afterEach() {
        membershipRepository.deleteByName(Membership.KAKAO)
        membershipRepository.deleteByName(Membership.NAVER)
        membershipRepository.deleteByName(Membership.COOPANG)
    }

    @Test
    fun `맴버쉽_등록`() {
        membershipRepository.save(Membership.KAKAO)
        val kakaoMembership: Membership? = membershipRepository.findByName(Membership.KAKAO)

        assertThat(kakaoMembership).isNotNull()
    }

    @Test
    fun `맴버쉽_등록_중복`() {
        membershipRepository.save(Membership.KAKAO)
        val kakaoMembership: Membership? = membershipRepository.findByName(Membership.KAKAO)

        assertThat(kakaoMembership).isNotNull()
        assertThatThrownBy { membershipRepository.save(Membership.KAKAO) }.isInstanceOf(DuplicateKeyException::class.java)
    }

    @Test
    fun `맴버쉽_전체_조회`() {
        membershipRepository.save(Membership.KAKAO)
        membershipRepository.save(Membership.NAVER)
        membershipRepository.save(Membership.COOPANG)

        val membershipList = membershipRepository.findAll()
        assertThat(membershipList).hasSize(3)
    }

    @Test
    fun `맴버쉽_이름_조회`() {
        membershipRepository.save(Membership.KAKAO)
        val kakaoMembership: Membership? = membershipRepository.findByName(Membership.KAKAO)
        assertThat(kakaoMembership?.name).isEqualTo(Membership.KAKAO.name)
    }

    @Test
    fun `맴버쉽_이름_삭제`() {
        membershipRepository.save(Membership.KAKAO)
        membershipRepository.deleteByName(Membership.KAKAO)

        val kakaoMembership: Membership? = membershipRepository.findByName(Membership.KAKAO)
        assertThat(kakaoMembership).isNull()
    }

    @TestConfiguration
    class Config(private val dataSource: DataSource) {
        @Bean
        fun membershipRepository(): MembershipRepository {
            return MembershipRepositoryImp(dataSource)
        }
    }
}