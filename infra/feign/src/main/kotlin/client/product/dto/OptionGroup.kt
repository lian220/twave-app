package client.product.dto

import java.math.BigDecimal

data class OptionGroup(
  val id: Long?,
  val name: String,
  val isRequired: String = "Y",
  val minCount: Int = 1,
  val maxCount: Int = 1,
  var options: List<Option>?
)

data class Option(
  val id: Long?,
  val name: String,
  val additionalPrice: BigDecimal = BigDecimal.ZERO,
  val isDeleted: Boolean = false
)