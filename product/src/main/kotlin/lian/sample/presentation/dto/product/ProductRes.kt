package lian.sample.presentation.dto.product

import lian.sample.presentation.dto.category.Category
import lian.sample.presentation.dto.optionGroup.OptionGroup
import java.math.BigDecimal

data class ProductRes(
  val id: Long,
  val name: String,
  val price: BigDecimal,
  val color: String,
  var category: Category,
  var optionGroup: OptionGroup?,
  val isTaxIncluded: Boolean,
  val status: String
)