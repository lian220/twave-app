package lian.sample.domain.entity

import jakarta.persistence.*
import java.time.Instant

@Entity
@Table(name = "CATEGORY")
data class CategoryEntity(
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "CATEGORY_ID")
  val id: Long = 0,

  @Column(name = "NAME", nullable = false, length = 50)
  val name: String,

  @Column(name = "CREATED_AT", nullable = false, updatable = false)
  val createdAt: Instant = Instant.now()
)