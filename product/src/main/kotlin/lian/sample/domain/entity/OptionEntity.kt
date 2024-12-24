package lian.sample.domain.entity

import jakarta.persistence.*
import lian.sample.jpa.BooleanToYNConverter
import org.hibernate.annotations.DynamicUpdate
import java.math.BigDecimal
import java.time.Instant

@Entity
@Table(name = "`OPTION`")
@DynamicUpdate
data class OptionEntity(
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "OPTION_ID")
  val id: Long = 0,

  @Column(name = "NAME", nullable = false)
  val name: String,

  @Column(name = "ADDITIONAL_PRICE", precision = 10, scale = 2)
  val additionalPrice: BigDecimal = BigDecimal.ZERO,

  @Column(name = "IS_DELETED", length = 1, nullable = false, columnDefinition = "CHAR(1) DEFAULT 'N'")
  @Convert(converter = BooleanToYNConverter::class)
  private var isDeleted: Boolean? = false,

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "OPTION_GROUP_ID")
  val optionGroup: OptionGroupEntity,

  @Column(name = "CREATED_AT", nullable = false, updatable = false)
  val createdAt: Instant = Instant.now()
)
