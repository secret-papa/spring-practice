package hello.springpractice.membership.repository

import hello.springpractice.membership.domain.Order
import hello.springpractice.membership.domain.OrderItem
import hello.springpractice.membership.domain.Product
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.queryForObject
import org.springframework.stereotype.Repository
import javax.sql.DataSource

@Repository
class OrderRepositoryImp(dataSource: DataSource): OrderRepository {
    private val template = JdbcTemplate(dataSource)

    override fun save(order: Order): Order {
        val insertOrderSql = "insert into `order` (id, memberId, createdAt) values (?, ?, ?)"
        val insertOrderItemSql = "insert into orderItem (orderId, productId, quantity) values (?, ?, ?)"

        template.update(insertOrderSql, order.id, order.memberId, order.createdAt)
        order.items.forEach {
           template.update(insertOrderItemSql, order.id, it.product.id, it.quantity)
        }

        return order
    }

//    override fun findAll(): List<Order> {
//        val sql = """
//            SELECT
//                o.id AS orderId,
//                o.memberId AS orderMemberId,
//                o.createdAt AS orderCreatedAt,
//                oi.quantity AS orderItemQuantity,
//                p.id AS productId,
//                p.name AS productName,
//                p.price AS productPrice
//            FROM `order` o
//            JOIN orderItem oi ON o.id = oi.orderId
//            JOIN product p ON p.id = oi.productId
//        """.trimIndent()
//
//    }

    override fun findById(id: String): Order? {
        val sql = """
            SELECT 
                o.id AS orderId, 
                o.memberId AS orderMemberId, 
                o.createdAt AS orderCreatedAt, 
                oi.quantity AS orderItemQuantity, 
                p.id AS productId, 
                p.name AS productName, 
                p.price AS productPrice 
            FROM `order` o 
            JOIN orderItem oi ON o.id = oi.orderId 
            JOIN product p ON p.id = oi.productId 
            WHERE o.id = ?
        """.trimIndent()
        var order: Order? = null

        template.query(sql, {rs, _ ->
            val orderItem = OrderItem(
                product = Product(
                    id = rs.getString("productId"),
                    name = rs.getString("productName"),
                    price = rs.getInt("productPrice")
                ),
                quantity = rs.getInt("orderItemQuantity"),
            )
            if (order == null) {
                order = Order(
                    id = rs.getString("orderId"),
                    memberId = rs.getString("memberId"),
                    createdAt = rs.getTimestamp("orderCreatedAt").toInstant(),
                    items = mutableListOf(orderItem)
                )
            } else {
                order!!.addItem(orderItem)
            }
        }, id)

        return order
    }
}