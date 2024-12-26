package hello.springpractice.membership.repository

import hello.springpractice.membership.domain.Product
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository
import javax.sql.DataSource

@Repository
class ProductRepositoryImp(dataSource: DataSource): ProductRepository {
    private val template = JdbcTemplate(dataSource)
    private val productRowMapper: RowMapper<Product> = RowMapper { rs, _ -> Product(rs.getString("id"), rs.getString("name"), rs.getInt("price")) }

    override fun save(product: Product): Product {
        val sql = "insert into product (id, name, price) values (?, ?, ?)"
        template.update(sql, product.id, product.name, product.price)

        return product
    }

    override fun findAll(): List<Product> {
        val sql = "select * from product"
        return template.query(sql, productRowMapper)
    }

    override fun findById(id: String): Product? {
        try {
            val sql = "select * from product where id = ?"
            return template.queryForObject(sql, productRowMapper, id)
        } catch (ex: EmptyResultDataAccessException) {
            return null
        }
    }
}