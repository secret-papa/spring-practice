package hello.springpractice.membership.domain

interface DiscountPolicy {
    fun calc(originPrice: Int): Int
}