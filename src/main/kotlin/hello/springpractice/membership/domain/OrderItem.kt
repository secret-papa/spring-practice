package hello.springpractice.membership.domain

data class OrderItem(
    val product: Product,
    val quantity: Int
) {
    fun getPrice(): Int {
        return product.price.times(quantity)
    }
}