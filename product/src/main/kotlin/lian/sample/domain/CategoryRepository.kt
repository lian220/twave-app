package lian.sample.domain

import lian.sample.domain.entity.CategoryEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CategoryRepository : JpaRepository<CategoryEntity, Long> {
  fun findByName(name: String): CategoryEntity?
}