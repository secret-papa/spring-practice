package hello.springpractice.membership.repository

import hello.springpractice.membership.domain.Product

interface ProductRepository {
    fun save(product: Product): Product;
    fun findAll(): List<Product>;
    fun findById(id: String): Product?;
}