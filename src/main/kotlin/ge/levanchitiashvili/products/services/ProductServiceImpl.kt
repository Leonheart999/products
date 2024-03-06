package ge.levanchitiashvili.products.services

import ge.levanchitiashvili.products.models.Product
import ge.levanchitiashvili.products.repository.jpa.ProductRepository
import jakarta.transaction.Transactional
import lombok.RequiredArgsConstructor
import org.apache.commons.lang3.BooleanUtils
import org.springframework.stereotype.Service
import java.util.*

@Service
@RequiredArgsConstructor
class ProductServiceImpl(private val productRepository: ProductRepository) : ProductService {
    override fun getProducts(active: Boolean): List<Product> {
        return productRepository.findByActiveTrue(active)
    }

    override fun getProduct(id: Long): Product {
        val product: Optional<Product> = productRepository.findByIdAndActiveTrue(id)
        if (product.isEmpty) {
            throw RuntimeException("product with id= $id doesn't exist")
        } else {
            return product.get()
        }
    }
    @Transactional
    override fun save(product: Product): Product {
        return productRepository.save(product)
    }

    @Transactional
    override fun delete(id: Long, softDelete: Boolean) {
        val product: Product = getProduct(id)
        if (BooleanUtils.isTrue(softDelete)) {
            product.active = false
            save(product)
        } else {
            productRepository.delete(product)
        }
    }

    @Transactional
    override fun edit(id: Long,product: Product): Product {
        val oldProduct: Product=getProduct(id);
        oldProduct.name=product.name
        return save(oldProduct)
    }
}