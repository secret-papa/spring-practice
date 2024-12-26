package hello.springpractice.membership.domain

enum class Partnership {
    KAKAO,
    NAVER,
    COOPANG,
    KAKAO_Test,
    NAVER_Test,
    COOPANG_Test,
}

data class Membership(val code: Number, val partnership: Partnership)