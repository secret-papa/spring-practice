package hello.springpractice.membership.repository

import hello.springpractice.membership.domain.Order
import hello.springpractice.membership.domain.OrderItem
import hello.springpractice.membership.domain.Product
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import javax.sql.DataSource

@Repository
class OrderRepositoryImp(dataSource: DataSource): OrderRepository {
    private val template = JdbcTemplate(dataSource)

    override fun save(order: Order): Order {
        val insertOrderSql = "insert into `order` (id, memberId, originPrice, discountPrice, totalPrice, createdAt) values (?, ?, ?, ?, ? ,?)"
        val insertOrderItemSql = "insert into orderItem (orderId, productId, quantity) values (?, ?, ?)"

        template.update(insertOrderSql, order.id, order.memberId, order.originalPrice, order.discountPrice, order.totalPrice, order.createdAt)
        order.items.forEach {
           template.update(insertOrderItemSql, order.id, it.product.id, it.quantity)
        }

        return order
    }

    override fun findById(id: String): Order? {
        val sql = """
            SELECT 
                o.id AS orderId, 
                o.memberId AS orderMemberId, 
                o.createdAt AS orderCreatedAt, 
                o.originPrice AS orderOriginPrice,
                o.discountPrice AS orderDiscountPrice,
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
                    originalPrice = rs.getInt("orderOriginPrice"),
                    discountPrice = rs.getInt("orderDiscountPrice"),
                    createdAt = rs.getTimestamp("orderCreatedAt").toInstant(),
                    items = mutableListOf(orderItem)
                )
            } else {
                order!!.addItem(orderItem)
            }
        }, id)

        return order
    }

    override fun findByMemberId(memberId: String): List<Order> {
        val sql = """
            SELECT 
                o.id AS orderId, 
                o.memberId AS orderMemberId, 
                o.originPrice AS orderOriginPrice,
                o.discountPrice AS orderDiscountPrice,
                o.createdAt AS orderCreatedAt, 
                oi.quantity AS orderItemQuantity, 
                p.id AS productId, 
                p.name AS productName, 
                p.price AS productPrice 
            FROM `order` o 
            JOIN orderItem oi ON o.id = oi.orderId 
            JOIN product p ON p.id = oi.productId 
            WHERE o.memberId = ?
        """.trimIndent()

        val orders = hashMapOf<String, Order>()

        template.query(sql, {rs, _ ->
            val orderItem = OrderItem(
                product = Product(
                    id = rs.getString("productId"),
                    name = rs.getString("productName"),
                    price = rs.getInt("productPrice")
                ),
                quantity = rs.getInt("orderItemQuantity"),
            )

            if (!orders.containsKey(rs.getString("orderId"))) {
                val order = Order(
                    id = rs.getString("orderId"),
                    memberId = rs.getString("memberId"),
                    originalPrice = rs.getInt("orderOriginPrice"),
                    discountPrice = rs.getInt("orderDiscountPrice"),
                    createdAt = rs.getTimestamp("orderCreatedAt").toInstant(),
                    items = mutableListOf(orderItem)
                )
                orders[rs.getString("orderId")] = order
            } else {
                orders[rs.getString("orderId")]?.addItem(orderItem)
            }

        }, memberId)

        return orders.values.toList()
    }
}