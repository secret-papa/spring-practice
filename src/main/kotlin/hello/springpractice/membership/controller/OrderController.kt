package hello.springpractice.membership.controller

import hello.springpractice.membership.domain.Order
import hello.springpractice.membership.domain.OrderItem
import hello.springpractice.membership.repository.MemberRepository
import hello.springpractice.membership.repository.OrderRepository
import hello.springpractice.membership.repository.ProductRepository
import hello.springpractice.membership.service.dto.OrderDto
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/apis/orders")
class OrderController(
    private val memberRepository: MemberRepository,
    private val productRepository: ProductRepository,
    private val orderRepository: OrderRepository
) {
    @PostMapping("/")
    fun createOrder(@RequestBody orderDto: OrderDto): Order {
        memberRepository.findById(orderDto.memberId) ?: throw NotFoundException()

        val orderItems = orderDto.items.map { orderItem ->
            val product = productRepository.findById(orderItem.productId) ?: throw NotFoundException()
            OrderItem(
                product = product,
                quantity = orderItem.quantity
            )}

        val order = Order(
            memberId = orderDto.memberId,
            items = orderItems,
        )

        return orderRepository.save(order)
    }
}