package lian.sample.domain.custom

import lian.sample.app.dto.SearchConditionDto
import lian.sample.presentation.dto.product.ProductRes

interface ProductRepositoryCustom {
  fun findAllBySearchCondition(searchConditionDto: SearchConditionDto?): List<ProductRes>
}