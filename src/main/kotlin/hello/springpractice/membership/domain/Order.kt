package hello.springpractice.membership.domain

import java.time.Instant
import java.util.*

class Order(
    val id: String = UUID.randomUUID().toString(),
    val memberId: String,
    val items: MutableList<OrderItem>,
    val discountPrice: Int = 0,
    val originalPrice: Int = 0,
    val totalPrice: Int = 0,
    val createdAt: Instant = Instant.now(),
) {
    fun addItem(item: OrderItem) {
        items.add(item)
    }

}
