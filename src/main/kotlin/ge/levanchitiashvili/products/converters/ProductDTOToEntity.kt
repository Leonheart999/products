package ge.levanchitiashvili.products.converters

import ge.levanchitiashvili.products.dtos.ProductDTO
import ge.levanchitiashvili.products.models.Product
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component

@Component
class ProductDTOToEntity: Converter<ProductDTO, Product> {
    override fun convert(productDTO: ProductDTO): Product {
        return Product(productDTO.id, productDTO.name, productDTO.quantity, productDTO.price, productDTO.active)
    }
}