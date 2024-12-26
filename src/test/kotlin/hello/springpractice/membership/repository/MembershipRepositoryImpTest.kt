package hello.springpractice.membership.repository

import hello.springpractice.membership.domain.Membership
import hello.springpractice.membership.domain.Partnership
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
        membershipRepository.deleteByCode(400)
        membershipRepository.deleteByCode(500)
        membershipRepository.deleteByCode(600)
    }

    @Test
    fun `맴버쉽_등록`() {
        val memberShip = Membership(code = 400, partnership = Partnership.KAKAO_Test)
        membershipRepository.save(memberShip)

        val kakaoMembership: Membership? = membershipRepository.findByCode(memberShip.code)
        assertThat(kakaoMembership).isNotNull()
    }

    @Test
    fun `맴버쉽_등록_중복`() {
        val membership1 = Membership(code = 400, partnership = Partnership.KAKAO_Test)
        val sameAsMembership1 = Membership(code = 500, partnership = Partnership.KAKAO_Test)
        membershipRepository.save(membership1)


        assertThatThrownBy { membershipRepository.save(sameAsMembership1) }.isInstanceOf(DuplicateKeyException::class.java)
    }

    @Test
    fun `맴버쉽_전체_조회`() {
        val kakaoMemberShip = Membership(code = 400, partnership = Partnership.KAKAO_Test)
        val naverMembership = Membership(code = 500, partnership = Partnership.NAVER_Test)
        val coopangMembership = Membership(code = 600, partnership = Partnership.COOPANG_Test)
        membershipRepository.save(kakaoMemberShip)
        membershipRepository.save(naverMembership)
        membershipRepository.save(coopangMembership)

        val membershipList = membershipRepository.findAll()
        assertThat(membershipList).hasSize(3)
    }

    @Test
    fun `맴버쉽_파트너쉽_조회`() {
        val kakaoMemberShip = Membership(code = 400, partnership = Partnership.KAKAO_Test)
        membershipRepository.save(kakaoMemberShip)
        val kakaoMembership: Membership? = membershipRepository.findByPartnership(kakaoMemberShip.partnership)
        assertThat(kakaoMembership?.partnership?.name).isEqualTo(Partnership.KAKAO_Test.name)
    }

    @Test
    fun `맴버쉽_코드_삭제`() {
        val kakaoMemberShip = Membership(code = 400, partnership = Partnership.KAKAO_Test)
        membershipRepository.save(kakaoMemberShip)
        membershipRepository.deleteByCode(kakaoMemberShip.code)

        val findMembership: Membership? = membershipRepository.findByCode(kakaoMemberShip.code)
        assertThat(findMembership).isNull()
    }

    @TestConfiguration
    class Config(private val dataSource: DataSource) {
        @Bean
        fun membershipRepository(): MembershipRepository {
            return MembershipRepositoryImp(dataSource)
        }
    }
}