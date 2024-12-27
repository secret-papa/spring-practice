package hello.springpractice.membership.domain

class DiscountPolicyImp(
    private  val membership: List<Membership>
): DiscountPolicy {
    override fun calc(originPrice: Int): Int {
        if (membership.any { it.partnership == Partnership.KAKAO || it.partnership == Partnership.KAKAO_Test}) {
            return 1000
        } else if (membership.any { it.partnership == Partnership.NAVER || it.partnership == Partnership.NAVER_Test}) {
            return (originPrice * 0.1).toInt()
        } else if (membership.any { it.partnership == Partnership.COOPANG || it.partnership == Partnership.COOPANG_Test}) {
            return (originPrice * 0.5).toInt()
        }

        return 0
    }
}