package lian.sample.domain

import lian.sample.domain.entity.OptionGroupEntity
import org.springframework.data.jpa.repository.JpaRepository

interface OptionGroupRepository: JpaRepository<OptionGroupEntity, Long> {
}