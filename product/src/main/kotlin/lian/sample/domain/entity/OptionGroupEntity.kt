package lian.sample.domain.entity

import jakarta.persistence.*
import lian.sample.jpa.BooleanToYNConverter
import org.hibernate.annotations.DynamicUpdate
import java.time.Instant

@Entity
@Table(name = "OPTION_GROUP")
@DynamicUpdate
data class OptionGroupEntity(
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "OPTION_GROUP_ID")
  val id: Long = 0,

  @Column(name = "NAME", nullable = false)
  val name: String,

  @Column(name = "IS_REQUIRED", length = 1, nullable = false, columnDefinition = "CHAR(1) DEFAULT 'Y'")
  val isRequired: String = "Y",

  @Column(name = "MIN_COUNT", nullable = false, columnDefinition = "INT UNSIGNED DEFAULT 1")
  val minCount: Int = 1,

  @Column(name = "MAX_COUNT", nullable = false, columnDefinition = "INT UNSIGNED DEFAULT 1")
  val maxCount: Int = 1,

  @Column(name = "IS_DELETED", length = 1, nullable = false, columnDefinition = "CHAR(1) DEFAULT 'N'")
  @Convert(converter = BooleanToYNConverter::class)
  private var isDeleted: Boolean? = false,

  @Column(name = "CREATED_AT", nullable = false, updatable = false)
  val createdAt: Instant = Instant.now(),
)
