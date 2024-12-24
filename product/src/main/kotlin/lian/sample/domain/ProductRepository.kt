package lian.sample.domain

import lian.sample.domain.custom.ProductRepositoryCustom
import lian.sample.domain.entity.ProductEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ProductRepository : JpaRepository<ProductEntity, Long>, ProductRepositoryCustom {
  fun findByName(name: String): ProductEntity?
}