package hello.springpractice.membership.controller

import hello.springpractice.membership.domain.Order
import hello.springpractice.membership.repository.OrderRepository
import hello.springpractice.membership.service.OrderService
import hello.springpractice.membership.service.dto.OrderDto
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/apis/orders")
class OrderController(
    private val orderService: OrderService,
    private val orderRepository: OrderRepository
) {
    @GetMapping("/{id}")
    fun findOrder(@PathVariable id: String): Order? {
        return orderRepository.findById(id)
    }

    @PostMapping("/")
    fun createOrder(@RequestBody orderDto: OrderDto): Order {
        return orderService.createOrder(orderDto)
    }
}