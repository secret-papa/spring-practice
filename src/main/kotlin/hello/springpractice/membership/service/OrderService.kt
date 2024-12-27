package hello.springpractice.membership.service

import hello.springpractice.membership.domain.Order
import hello.springpractice.membership.service.dto.OrderDto

interface OrderService {
    fun createOrder(orderDto: OrderDto): Order
}