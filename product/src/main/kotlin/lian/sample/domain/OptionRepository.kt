package lian.sample.domain

import lian.sample.domain.entity.OptionEntity
import lian.sample.domain.entity.OptionGroupEntity
import org.springframework.data.jpa.repository.JpaRepository

interface OptionRepository: JpaRepository<OptionEntity, Long> {
  fun findAllByOptionGroup(optionGroup: OptionGroupEntity): List<OptionEntity>
}