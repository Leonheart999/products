package ge.levanchitiashvili.products.services

import ge.levanchitiashvili.products.models.Product
import ge.levanchitiashvili.products.repository.jpa.ProductRepository
import jakarta.persistence.criteria.Predicate
import jakarta.transaction.Transactional
import lombok.RequiredArgsConstructor
import org.apache.commons.lang3.BooleanUtils
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.util.*

@Service
@RequiredArgsConstructor
class ProductServiceImpl(private val productRepository: ProductRepository) : ProductService {
    override fun getProducts(name: String?, price: BigDecimal?, quantity: Int?, active: Boolean): List<Product> {
        val searchName= "%$name%"
        return productRepository.findAll { root, query, cb ->
            var predicate: Predicate = cb.conjunction()
            predicate = cb.and(predicate, cb.equal(root.get<Boolean>(Product.ACTIVE), true))
            if (name!=null && name.isNotBlank()) {
                predicate = cb.and(predicate, cb.like(root.get<String>(Product.NAME),  searchName))
            }
            if (price!=null) {
                predicate = cb.and(predicate, cb.equal(root.get<BigDecimal>(Product.PRICE), price))
            }
            if (quantity!=null) {
                predicate = cb.and(predicate, cb.equal(root.get<Int>(Product.QUANTITY), quantity))
            }
            predicate
        }
//        return productRepository.findByActiveTrue(active)
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
    override fun edit(id: Long, product: Product): Product {
        val oldProduct: Product = getProduct(id);
        oldProduct.name = product.name
        return save(oldProduct)
    }
}