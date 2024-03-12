package ge.levanchitiashvili.products.converters

import ge.levanchitiashvili.products.dtos.ProductDTO
import ge.levanchitiashvili.products.models.Product
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component


@Component
class ProductToDTO : Converter<Product, ProductDTO> {
    override fun convert(product: Product): ProductDTO {
        return ProductDTO(product.id, product.name, product.quantity, product.price, product.active)
    }
}