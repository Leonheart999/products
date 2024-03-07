package ge.levanchitiashvili.products.controllers

import ge.levanchitiashvili.products.models.Product
import ge.levanchitiashvili.products.services.ProductService
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
class ProductController(private final val productService: ProductService) {

    @GetMapping
    fun getAll(@RequestParam(required = false) name: String?,
               @RequestParam(required = false) price: BigDecimal?,
               @RequestParam(required = false) quantity: Int?,
               @RequestParam(required = false, defaultValue = "true") active: Boolean): List<Product> {
        val products :List<Product> = productService.getProducts(name, price, quantity, active)
        return products
    }

    @GetMapping("{id}")
    fun get(@PathVariable id: Long): Product {
        return productService.getProduct(id);
    }


    @PostMapping
    fun add(@RequestBody product: Product): Product? {
        return productService.save(product);
    }

    @PutMapping("{id}")
    fun edit(@PathVariable id: Long, @RequestBody product: Product): Product {
        return productService.edit(id, product)
    }

    @DeleteMapping("{id}")
    fun delete(@PathVariable id: Long, @RequestParam(required = false, defaultValue = "true") softDelete: Boolean) {
        productService.delete(id, softDelete)
    }


}