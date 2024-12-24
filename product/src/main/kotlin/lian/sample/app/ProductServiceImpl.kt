package lian.sample.app

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import lian.sample.app.dto.SearchConditionDto
import lian.sample.domain.CategoryRepository
import lian.sample.domain.OptionGroupRepository
import lian.sample.domain.OptionRepository
import lian.sample.domain.ProductRepository
import lian.sample.domain.entity.ProductEntity
import lian.sample.exception.NoSuchCategoryException
import lian.sample.exception.NoSuchProductException
import lian.sample.presentation.dto.category.Category
import lian.sample.presentation.dto.optionGroup.Option
import lian.sample.presentation.dto.optionGroup.OptionGroup
import lian.sample.presentation.dto.product.Product
import lian.sample.presentation.dto.product.ProductRes
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ProductServiceImpl(
  private val productRepository: ProductRepository,
  private val categoryRepository: CategoryRepository,
  private val optionGroupRepository: OptionGroupRepository,
  private val optionRepository: OptionRepository,
  private val objectMapper: ObjectMapper
): ProductUseCase {

  @Transactional
  override fun createProduct(product: Product) {
    categoryRepository.findByIdOrNull(product.categoryId)
      ?.let {
        val optionGroup = product.optionGroupId ?. let { optionGroupRepository.findByIdOrNull(product.optionGroupId) }
        productRepository.save(product.toEntity(null, it, optionGroup))
      }
      ?: throw NoSuchCategoryException("categoryId: ${product.categoryId}")
  }

  @Transactional(readOnly = true)
  override fun getProduct(id: Long): ProductRes {
    return productRepository.findByIdOrNull(id)
      ?.let { convertEntityToProduct(it) }
      ?: throw NoSuchProductException("productId: ${id}")
  }

  @Transactional(readOnly = true)
  override fun getAllProducts(categoryId: Long?, productIds: List<Long>?): List<ProductRes> {
    return productRepository.findAllBySearchCondition(SearchConditionDto(categoryId, productIds))
  }

  @Transactional
  override fun deleteProduct(id: Long) {
    productRepository.findByIdOrNull(id) ?.apply {isDeleted(true)} ?: { throw NoSuchProductException("productId: ${id}") }
  }

  @Transactional
  override fun updateProduct(id: Long, product: Product) {
    categoryRepository.findByIdOrNull(product.categoryId)
      ?.let {
        val optionGroup = product.optionGroupId ?. let { optionGroupRepository.findByIdOrNull(product.optionGroupId) }
        productRepository.save(product.toEntity(id, it, optionGroup))
      }
      ?: throw NoSuchCategoryException("categoryId: ${product.categoryId}")
  }

  private fun convertEntityToProduct(product: ProductEntity): ProductRes {
    val category = objectMapper.convertValue(product.categoryEntity, Category::class.java)
    val optionGroup = product.optionGroup?.let {
      val options = optionRepository.findAllByOptionGroup(product.optionGroup)
      val optionGroupDto = objectMapper.convertValue(product.optionGroup, OptionGroup::class.java)
      optionGroupDto.options = objectMapper.convertValue(options, object : TypeReference<List<Option>>() {})
      optionGroupDto
    }
    return ProductRes(
      id = product.id,
      name = product.name,
      price = product.price,
      color = product.color,
      category = category,
      optionGroup = optionGroup,
      isTaxIncluded = product.isTaxIncluded,
      status = product.status
    )
  }
}