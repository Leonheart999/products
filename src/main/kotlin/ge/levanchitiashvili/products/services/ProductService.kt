package ge.levanchitiashvili.products.services

import ge.levanchitiashvili.products.models.Product
import java.math.BigDecimal

interface ProductService {

    fun getProducts(name: String?, price: BigDecimal?, quantity: Int?, active: Boolean): List<Product>

    fun getProduct(id: Long): Product

    fun save(product: Product): Product
    fun delete(id: Long, softDelete: Boolean)
    fun edit(id: Long,product: Product): Product

}