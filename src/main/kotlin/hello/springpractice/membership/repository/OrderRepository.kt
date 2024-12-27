package hello.springpractice.membership.repository

import hello.springpractice.membership.domain.Order

interface OrderRepository {
    fun save(order: Order): Order
    fun findById(id: String): Order?
    fun findByMemberId(memberId: String): List<Order>
}