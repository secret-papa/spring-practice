package hello.springpractice.membership.controller

import hello.springpractice.membership.domain.Product
import hello.springpractice.membership.repository.ProductRepository
import hello.springpractice.membership.service.dto.ProductDto
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/apis/products")
class ProductController(
    private val productRepository: ProductRepository
) {
    @GetMapping("/")
    fun findAll(): List<Product> {
        return productRepository.findAll()
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: String): Product? {
        return productRepository.findById(id)
    }

    @PostMapping("/")
    fun save(@RequestBody productDto: ProductDto): Product {
        val product = Product(name = productDto.name, price = productDto.price)

        return productRepository.save(product)
    }
}