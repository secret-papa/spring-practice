package hello.springpractice.membership.service.dto

data class OrderItemDto(
    val quantity: Int,
    val productId: String
)

data class OrderDto(
    val memberId: String,
    val items: List<OrderItemDto>
)