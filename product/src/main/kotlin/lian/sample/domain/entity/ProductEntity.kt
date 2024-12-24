package lian.sample.domain.entity

import jakarta.persistence.*
import lian.sample.jpa.BooleanToYNConverter
import org.hibernate.annotations.DynamicUpdate
import java.math.BigDecimal
import java.time.Instant

@Entity
@Table(name = "PRODUCT")
@DynamicUpdate
data class ProductEntity(
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "PRODUCT_ID")
  val id: Long = 0,

  @Column(name = "NAME", nullable = false)
  val name: String,

  @Column(name = "PRICE", precision = 10, scale = 2, nullable = false)
  val price: BigDecimal,

  @Column(name = "COLOR", nullable = false, length = 50)
  val color: String,

  @Column(name = "IS_TAX_INCLUDED", length = 1, nullable = false, columnDefinition = "CHAR(1) DEFAULT 'Y'")
  @Convert(converter = BooleanToYNConverter::class)
  val isTaxIncluded: Boolean = true,

  @Column(name = "STATUS", nullable = false, length = 20, columnDefinition = "VARCHAR(20) DEFAULT 'AVAILABLE'")
  val status: String = "AVAILABLE",

  @Column(name = "IS_DELETED", length = 1, nullable = false, columnDefinition = "CHAR(1) DEFAULT 'N'")
  @Convert(converter = BooleanToYNConverter::class)
  private var isDeleted: Boolean = false,

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "CATEGORY_ID")
  val categoryEntity: CategoryEntity,

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "OPTION_GROUP_ID")
  val optionGroup: OptionGroupEntity? = null,

  @Column(name = "CREATED_AT", nullable = false, updatable = false)
  val createdAt: Instant = Instant.now(),

  @Column(name = "UPDATED_AT", nullable = false)
  val updatedAt: Instant = Instant.now()
) {
  fun isDeleted(isDeleted: Boolean) {
    this.isDeleted = isDeleted
  }
}
