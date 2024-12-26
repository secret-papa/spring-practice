package hello.springpractice.membership.repository

import hello.springpractice.membership.domain.Order
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import javax.sql.DataSource

@Repository
class OrderRepositoryImp(dataSource: DataSource): OrderRepository {
    private val template = JdbcTemplate(dataSource)

    override fun save(order: Order): Order {
        val sql = "insert into `order` (id, memberId, createdAt) values (?, ?, ?)"
        val sql2 = "insert into order_product (orderId, productId) values (?, ?)"

        template.update(sql, order.id, order.memberId, order.createdAt)
        order.products.forEach { template.update(sql2, order.id, it.id)}

        return order
    }

    override fun findById(id: String): Order {
        return Order(products = listOf(), memberId = "")
    }
}