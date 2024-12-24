package lian.sample.app

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import lian.sample.domain.OptionGroupRepository
import lian.sample.domain.OptionRepository
import lian.sample.domain.entity.OptionEntity
import lian.sample.domain.entity.OptionGroupEntity
import lian.sample.exception.NoSuchOptionGroupException
import lian.sample.exception.OptionValidationException
import lian.sample.presentation.dto.optionGroup.Option
import lian.sample.presentation.dto.optionGroup.OptionGroup
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class OptionGroupServiceImpl(
  private val optionGroupRepository: OptionGroupRepository,
  private val optionRepository: OptionRepository,
  private val objectMapper: ObjectMapper
): OptironGroupUseCase {
  @Transactional
  override fun createOptionGroup(optionGroup: OptionGroup) {
    val optionGroupEntity = optionGroupRepository.save(OptionGroupEntity(
      name = optionGroup.name,
      isRequired = optionGroup.isRequired,
      minCount = optionGroup.minCount,
      maxCount = optionGroup.maxCount,
    ))

    optionGroup.options?.forEach { option ->
      val optionEntity = OptionEntity(
        name = option.name,
        optionGroup = optionGroupEntity
      )
      optionRepository.save(optionEntity)
    }
  }

  @Transactional(readOnly = true)
  override fun getOptionGroup(id: Long): OptionGroup {
    return optionGroupRepository.findByIdOrNull(id)
      ?.let { optionGroup ->
        val options = optionRepository.findAllByOptionGroup(optionGroup)
        val optionGroupDto = objectMapper.convertValue(optionGroup, OptionGroup::class.java)
        optionGroupDto.options = objectMapper.convertValue(options, object : TypeReference<List<Option>>() {})
        optionGroupDto
      } ?: let {
        throw NoSuchOptionGroupException("optionGroupId: ${id}")
      }
  }

  override fun updateOptionGroup(id: Long, optionGroup: OptionGroup) {
    val optionGroupEntity = optionGroupRepository.save(OptionGroupEntity(
      id= id,
      name = optionGroup.name,
      isRequired = optionGroup.isRequired,
      minCount = optionGroup.minCount,
      maxCount = optionGroup.maxCount,
      isDeleted = optionGroup.isDeleted
    ))

    optionGroup.options?.forEach { option ->
      option.id
        ?.let {
          val optionEntity = OptionEntity(
            id = option.id!!,
            name = option.name,
            optionGroup = optionGroupEntity,
            isDeleted = option.isDeleted
          )
          optionRepository.save(optionEntity)
        }
        ?: let {
          throw OptionValidationException()
        }
    }
  }
}