package hello.springpractice.membership.domain

import java.time.Instant
import java.util.*

class Order(
    val id: String = UUID.randomUUID().toString(),
    val memberId: String,
    val products: List<Product>,
    val createdAt: Instant = Instant.now(),
)