package ge.levanchitiashvili.products.services

import ge.levanchitiashvili.products.models.Product

interface ProductService {

    fun getProducts(active: Boolean): List<Product>

    fun getProduct(id: Long): Product

    fun save(product: Product): Product
    fun delete(id: Long, softDelete: Boolean)
    fun edit(id: Long,product: Product): Product

}