package hello.springpractice.membership.domain

import java.time.Instant
import java.util.*

class Order(
    val id: String = UUID.randomUUID().toString(),
    val memberId: String,
    val items: List<OrderItem>,
    val createdAt: Instant = Instant.now(),
) {
    fun getTotalPrice(): Int {
        return items.map { it.getPrice() }.sum()
    }
}
