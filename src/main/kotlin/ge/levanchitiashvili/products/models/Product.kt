package ge.levanchitiashvili.products.models

import jakarta.persistence.*
import lombok.Data
import lombok.experimental.FieldNameConstants
import java.math.BigDecimal

@Entity
@Table(name = "products")
@FieldNameConstants
@SequenceGenerator(name = "productsIdSeq", sequenceName = "products_id_seq", allocationSize = 1)
data class Product(
        @Id
        @GeneratedValue(generator = "productsIdSeq", strategy = GenerationType.SEQUENCE)
        @Column(name = "id", nullable = false)
        var id: Long,
        @Column(name = "name")
        var name: String,
        @Column(name = "quantity")
        var quantity: Int,
        @Column(name = "price")
        var price: BigDecimal,
        @Column(name = "active")
        var active: Boolean
) {
    companion object {
        @JvmField
        val ACTIVE: String = "active"

        @JvmField
        val NAME: String = "name"

        @JvmField
        val PRICE: String = "price"

        @JvmField
        val QUANTITY: String = "quantity"
    }
}
