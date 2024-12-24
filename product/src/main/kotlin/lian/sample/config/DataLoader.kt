package lian.sample.config

import lian.sample.domain.CategoryRepository
import lian.sample.domain.ProductRepository
import lian.sample.domain.entity.CategoryEntity
import lian.sample.domain.entity.ProductEntity
import lian.sample.domain.type.ProductStatus
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import java.math.BigDecimal
import java.time.Instant


@Component
class DataLoader(
  private val categoryRepository: CategoryRepository,
  private val productRepository: ProductRepository
) : CommandLineRunner {
  override fun run(vararg args: String?) {
    val categoryData = listOf("음료","디저트")

    for (categoryName in categoryData) {
      val categoryEntity = categoryRepository.findByName(categoryName)
        ?: CategoryEntity(name = categoryName).also { categoryRepository.save(it) }

      if (categoryEntity.name == "음료") {
        productRepository.findByName("아메리카노")
          ?: let {
            val product = ProductEntity(
              name = "아메리카노",
              price = BigDecimal(4500),
              categoryEntity = categoryEntity,
              optionGroup = null,
              status = ProductStatus.AVAILABLE.toString(),
              color = "RED",
              createdAt = Instant.now(),
              updatedAt = Instant.now()
            )
            productRepository.save(product)
          }
      }
    }

  }
}
