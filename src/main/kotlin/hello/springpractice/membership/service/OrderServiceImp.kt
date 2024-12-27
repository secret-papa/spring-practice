package hello.springpractice.membership.service

import hello.springpractice.membership.domain.*
import hello.springpractice.membership.repository.MemberRepository
import hello.springpractice.membership.repository.OrderRepository
import hello.springpractice.membership.repository.ProductRepository
import hello.springpractice.membership.service.dto.OrderDto
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException
import org.springframework.stereotype.Service

@Service
class OrderServiceImp(
    private val memberRepository: MemberRepository,
    private val productRepository: ProductRepository,
    private val orderRepository: OrderRepository
): OrderService {
    override fun createOrder(orderDto: OrderDto): Order {
        memberRepository.findById(orderDto.memberId) ?: throw NotFoundException()

        val membership = memberRepository.findSignUpMembership(orderDto.memberId)

        val orderItems = orderDto.items.map { orderItem ->
            val product = productRepository.findById(orderItem.productId) ?: throw NotFoundException()
            OrderItem(
                product = product,
                quantity = orderItem.quantity
            )
        }.toMutableList()
        val originPrice: Int = orderItems.sumOf { it.getPrice() }
        val discountPrice = DiscountPolicyImp(membership).calc(originPrice)
        val totalPrice = originPrice - discountPrice

        val order = Order(
            memberId = orderDto.memberId,
            items = orderItems,
            originalPrice = originPrice,
            discountPrice = discountPrice,
            totalPrice = totalPrice
        )

        return orderRepository.save(order)
    }
}