package ge.levanchitiashvili.products.models

import jakarta.persistence.*
import lombok.Data
import lombok.experimental.FieldNameConstants

@Entity
@Table(name = "products")
@FieldNameConstants
@SequenceGenerator(name = "productsIdSeq", sequenceName = "products_id_seq", allocationSize = 1)
@Data
data class Product(
        @Id
        @GeneratedValue(generator = "productsIdSeq", strategy = GenerationType.SEQUENCE)
        @Column(name = "id", nullable = false)
        var id: Long,
        @Column(name = "name")
        var name: String,
        @Column(name = "active")
        var active: Boolean
)
