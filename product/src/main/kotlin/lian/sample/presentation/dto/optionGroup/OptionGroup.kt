package lian.sample.presentation.dto.optionGroup

import java.math.BigDecimal

data class OptionGroup(
  val name: String,
  val isRequired: String = "Y",
  val minCount: Int = 1,
  val maxCount: Int = 1,
  var options: List<Option>?,
  val isDeleted: Boolean? = false
)

data class Option(
  val id: Long?,
  val name: String,
  val additionalPrice: BigDecimal = BigDecimal.ZERO,
  val isDeleted: Boolean? = false
)