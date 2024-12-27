package hello.springpractice.membership.domain

import java.util.*

class OrderItem(
    val id: String = UUID.randomUUID().toString(),
    val product: Product,
    val quantity: Int
) {
    fun getPrice(): Int {
        return product.price.times(quantity)
    }
}