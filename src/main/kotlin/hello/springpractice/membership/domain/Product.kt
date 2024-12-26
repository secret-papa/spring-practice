package hello.springpractice.membership.domain

import java.util.*

class Product(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val price: Number
)