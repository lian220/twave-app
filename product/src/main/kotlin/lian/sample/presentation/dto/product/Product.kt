package lian.sample.presentation.dto.product

import kr.co.mustit.validation.annotation.ValueOfEnum
import lian.sample.domain.entity.CategoryEntity
import lian.sample.domain.entity.OptionGroupEntity
import lian.sample.domain.entity.ProductEntity
import lian.sample.domain.type.ProductStatus
import java.math.BigDecimal
import java.time.Instant

data class Product(
  val name: String,
  val price: BigDecimal,
  val color: String,
  val categoryId: Long,
  val optionGroupId: Long?,
  val isTaxIncluded: Boolean,
  @field:ValueOfEnum(enumClass = ProductStatus::class, message = "상품 상태가 올바르지 않습니다.")
  val status: String
) {
  fun toEntity(productId: Long?, categoryEntity: CategoryEntity, optionGroupEntity: OptionGroupEntity?): ProductEntity {
    return productId
      ?.let {
        ProductEntity(
          id = productId,
          name = name,
          price = price,
          categoryEntity = categoryEntity,
          optionGroup = optionGroupEntity,
          status = status,
          color = color,
          createdAt = Instant.now(),
          updatedAt = Instant.now()
        )
      }
      ?: let {
        ProductEntity(
          name = name,
          price = price,
          categoryEntity = categoryEntity,
          optionGroup = optionGroupEntity,
          status = status,
          color = color,
          createdAt = Instant.now(),
          updatedAt = Instant.now()
        )
      }
  }
}