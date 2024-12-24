package lian.sample.app

import lian.sample.presentation.dto.optionGroup.OptionGroup

interface OptironGroupUseCase {
  fun createOptionGroup(optionGroup: OptionGroup)
  fun getOptionGroup(id: Long): OptionGroup
  fun updateOptionGroup(id: Long, optionGroup: OptionGroup)
}