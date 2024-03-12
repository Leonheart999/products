package ge.levanchitiashvili.products.dtos


import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import lombok.Data
import java.math.BigDecimal

@Data
data class ProductDTO(var id: Long,
                      @field:NotBlank(message = "Name must not be blank")
                      var name: String,
                      var quantity: Int,
                      @field:NotNull(message = "Price cant be empty")
                      @field:Min(0, message = "Price must be a positive number")
                      var price: BigDecimal,
                      var active: Boolean)
