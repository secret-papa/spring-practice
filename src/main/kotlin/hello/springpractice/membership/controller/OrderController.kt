package hello.springpractice.membership.controller

import hello.springpractice.membership.domain.Order
import hello.springpractice.membership.repository.OrderRepository
import hello.springpractice.membership.repository.ProductRepository
import hello.springpractice.membership.service.dto.OrderDto
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/apis/orders")
class OrderController(
    private val productRepository: ProductRepository,
    private val orderRepository: OrderRepository
) {
    @PostMapping("/")
    fun createOrder(@RequestBody orderDto: OrderDto): Order {
        val products = orderDto.productIds.mapNotNull { productRepository.findById(it) }
        val order = Order(
            memberId = orderDto.memberId,
            products = products
        )

        return orderRepository.save(order)
    }
}