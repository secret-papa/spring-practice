package hello.springpractice.membership.service.dto

data class OrderDto(
    val memberId: String,
    val productIds: List<String>
)