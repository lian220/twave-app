package lian.sample.domain.custom

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.querydsl.core.types.dsl.BooleanExpression
import lian.sample.app.dto.SearchConditionDto
import lian.sample.domain.entity.ProductEntity
import lian.sample.domain.entity.QCategoryEntity.categoryEntity
import lian.sample.domain.entity.QOptionEntity.optionEntity
import lian.sample.domain.entity.QOptionGroupEntity.optionGroupEntity
import lian.sample.domain.entity.QProductEntity.productEntity
import lian.sample.presentation.dto.category.Category
import lian.sample.presentation.dto.optionGroup.Option
import lian.sample.presentation.dto.optionGroup.OptionGroup
import lian.sample.presentation.dto.product.ProductRes

class ProductRepositoryCustomImpl(
  private val objectMapper: ObjectMapper
) : ProductQueryDslRepositorySupport(ProductEntity::class.java),
  ProductRepositoryCustom {
  override fun findAllBySearchCondition(searchConditionDto: SearchConditionDto?): List<ProductRes> {
    val results = queryFactory.select(
        productEntity.id,
        productEntity.name,
        productEntity.price,
        productEntity.color,
        categoryEntity,
        optionGroupEntity,
        productEntity.isTaxIncluded,
        productEntity.status
      )
      .from(productEntity)
      .join(categoryEntity).on(categoryEntity.id.eq(productEntity.categoryEntity.id))
      .leftJoin(optionGroupEntity).on(optionGroupEntity.id.eq(productEntity.optionGroup.id))
      .where(
        eqCategory(searchConditionDto?.categoryId),
        eqProductIds(searchConditionDto?.productIds),
        productEntity.isDeleted.eq(false)
      )
      .fetch()

    val optionGroupIds = results.mapNotNull { it.get(optionGroupEntity)?.id }.distinct()
    val optionEntities = if (optionGroupIds.isNotEmpty()) {
      queryFactory.select(optionEntity)
        .from(optionEntity)
        .where(optionEntity.optionGroup.id.`in`(optionGroupIds))
        .fetch()
    } else {
      emptyList()
    }

    return results.map { tuple ->
      val category = tuple.get(categoryEntity)?.let {
        objectMapper.convertValue(it, Category::class.java)
      }!!

      val optionGroup = tuple.get(optionGroupEntity)?.let {optionGroup ->
        val options = optionEntities.filter { it.optionGroup.id ==  optionGroup.id}
        val optionGroupDto = objectMapper.convertValue(optionGroup, OptionGroup::class.java)
        optionGroupDto.options = objectMapper.convertValue(options, object : TypeReference<List<Option>>() {})
        optionGroupDto
      } ?: null

      ProductRes(
        id = tuple.get(productEntity.id)!!.toLong(),
        name = tuple.get(productEntity.name)!!,
        price = tuple.get(productEntity.price)!!,
        color = tuple.get(productEntity.color)!!,
        category = category,
        optionGroup = optionGroup,
        isTaxIncluded = tuple.get(productEntity.isTaxIncluded)!!,
        status = tuple.get(productEntity.status)!!
      )
    }
  }
  private fun eqCategory(catalogId: Long?) : BooleanExpression? =
    catalogId?.let { productEntity.categoryEntity.id.eq(catalogId) }

  private fun eqProductIds(productIds: List<Long>?) : BooleanExpression? =
    productIds?.let { productEntity.id.`in`(productIds) }
}