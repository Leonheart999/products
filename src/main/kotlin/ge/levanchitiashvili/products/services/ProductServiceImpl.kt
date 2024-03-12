package ge.levanchitiashvili.products.services

import com.sun.org.slf4j.internal.Logger
import com.sun.org.slf4j.internal.LoggerFactory
import ge.levanchitiashvili.products.dtos.ProductDTO
import ge.levanchitiashvili.products.models.Product
import ge.levanchitiashvili.products.repository.jpa.ProductRepository
import jakarta.persistence.criteria.Predicate
import jakarta.transaction.Transactional
import lombok.RequiredArgsConstructor
import org.apache.commons.lang3.BooleanUtils
import org.springframework.core.convert.ConversionService
import org.springframework.stereotype.Service
import java.math.BigDecimal
import kotlin.collections.ArrayList

@Service
@RequiredArgsConstructor
class ProductServiceImpl(private val productRepository: ProductRepository, val conversionService: ConversionService) : ProductService {
    val logger : Logger = LoggerFactory.getLogger(ProductServiceImpl::class.java)


    override fun getProducts(name: String?, price: BigDecimal?, quantity: Int?, active: Boolean): List<Product> {
        val searchName = "%$name%"
        logger.debug("name= $name  searchName= $searchName price= $price quantity=$quantity  active $active" )

        return productRepository.findAll { root, query, cb ->
            var predicate: Predicate = cb.conjunction()
            predicate = cb.and(predicate, cb.equal(root.get<Boolean>(Product.ACTIVE), true))
            if (!name.isNullOrBlank()) {
                predicate = cb.and(predicate, cb.like(root.get(Product.NAME), searchName))
            }
            if (price != null) {
                predicate = cb.and(predicate, cb.equal(root.get<BigDecimal>(Product.PRICE), price))
            }
            quantity?.let { predicate = cb.and(predicate, cb.equal(root.get<Int>(Product.QUANTITY), it)) }
            predicate
        }
//        return productRepository.findByActiveTrue(active)
    }

    override fun getProduct(id: Long): Product {
        logger.debug("id= $id" )
        return productRepository.findByIdAndActiveTrue(id).orElseThrow {
           throw RuntimeException("product with id= $id doesn't exist")
        }
    }

    @Transactional
    override fun saveDTO(productDTO: ProductDTO): Product {
        logger.debug("productDTO= $productDTO" )
        val product: Product?=DTOToENtity(productDTO)
        logger.debug("product= $product" )
        if(product==null){
            logger.error("problem saving product")
            throw RuntimeException("problem saving product")
        }
        return save(product)
    }

    @Transactional
    override fun save(product: Product): Product {
        logger.debug("product= $product" )
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
    override fun edit(id: Long, product: ProductDTO): Product {
        val oldProduct: Product = getProduct(id)
        logger.debug(" id= $id  oldProduct= $oldProduct" )
        oldProduct.name = product.name
        oldProduct.price = product.price
        oldProduct.quantity = product.quantity
        logger.debug(" edited= $oldProduct" )
        return save(oldProduct)
    }

    override fun entityToDTO(product: Product): ProductDTO? {
        return conversionService.convert(product, ProductDTO::class.java)
    }

    override fun entityToDTOList(list: List<Product>): List<ProductDTO> {
        val productDTOs: List<ProductDTO> = ArrayList()

        list.forEach { p ->
            val productDTO: ProductDTO? = entityToDTO(p)
            if (productDTO != null) {
                productDTOs.addLast(productDTO)
            }
        }
        return productDTOs
    }

    override fun DTOToENtity(productDTO: ProductDTO): Product? {
        return conversionService.convert(productDTO, Product::class.java)
    }
}
