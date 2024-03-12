package ge.levanchitiashvili.products.services

import ge.levanchitiashvili.products.dtos.ProductDTO
import ge.levanchitiashvili.products.models.Product
import java.math.BigDecimal

interface ProductService {

    fun getProducts(name: String?, price: BigDecimal?, quantity: Int?, active: Boolean): List<Product>

    fun getProduct(id: Long): Product

    fun saveDTO(productDTO: ProductDTO): Product
    fun save(product: Product): Product
    fun delete(id: Long, softDelete: Boolean)
    fun edit(id: Long, product: ProductDTO): Product

    fun entityToDTO(product: Product) :ProductDTO?

    fun entityToDTOList(list: List<Product>): List<ProductDTO>

    fun DTOToENtity(productDTO: ProductDTO) :Product?


}