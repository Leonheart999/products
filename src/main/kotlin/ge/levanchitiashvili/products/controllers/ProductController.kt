package ge.levanchitiashvili.products.controllers

import com.sun.org.slf4j.internal.Logger
import com.sun.org.slf4j.internal.LoggerFactory
import ge.levanchitiashvili.products.dtos.ProductDTO
import ge.levanchitiashvili.products.services.ProductService
import jakarta.validation.Valid
import lombok.RequiredArgsConstructor
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.math.BigDecimal

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
class ProductController(val productService: ProductService) {
     val logger : Logger = LoggerFactory.getLogger(ProductController::class.java)
    @GetMapping
    fun getAll(@RequestParam(required = false) name: String?,
               @RequestParam(required = false) price: BigDecimal?,
               @RequestParam(required = false) quantity: Int?,
               @RequestParam(required = false, defaultValue = "true") active: Boolean): List<ProductDTO> {
        logger.debug("name= $name  price= $price quantity=$quantity  active $active" )
        return productService.entityToDTOList(productService.getProducts(name, price, quantity, active))

    }

    @GetMapping("{id}")
    fun get(@PathVariable id: Long): ProductDTO? {
        logger.debug("id= $id" )

        return productService.entityToDTO(productService.getProduct(id))
    }


    @PostMapping
    fun add(@RequestBody @Valid product: ProductDTO): ProductDTO? {
        logger.debug("product= $product" )

        return productService.entityToDTO(productService.saveDTO(product))
    }

    @PutMapping("{id}")
    fun edit(@PathVariable id: Long, @RequestBody @Valid product: ProductDTO): ProductDTO? {
        logger.debug(" id= $id  product= $product" )
        return productService.entityToDTO(productService.edit(id, product))
    }

    @DeleteMapping("{id}")
    fun delete(@PathVariable id: Long, @RequestParam(required = false, defaultValue = "true") softDelete: Boolean) {
        logger.debug(" id= $id  softDelete= $softDelete" )
        productService.delete(id, softDelete)
    }


}