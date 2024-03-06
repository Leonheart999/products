package ge.levanchitiashvili.products.repository.jpa

import ge.levanchitiashvili.products.models.Product
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository
import java.util.*


@Repository
interface ProductRepository : JpaRepository<Product,Long>, JpaSpecificationExecutor<Product>{
    fun findByIdAndActiveTrue(id: Long): Optional<Product>
    fun findByActiveTrue(active: Boolean): List<Product>

}