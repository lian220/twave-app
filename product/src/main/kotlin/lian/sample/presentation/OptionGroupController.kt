package lian.sample.presentation

import io.swagger.v3.oas.annotations.Operation
import jakarta.validation.Valid
import lian.sample.app.OptironGroupUseCase
import lian.sample.presentation.dto.optionGroup.OptionGroup
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/option-groups")
class OptionGroupController(
  private val optionGroupUseCase: OptironGroupUseCase,
) {
  @Operation(summary = "옵션그룹 등록", description = "옵션그룹을 등록합니다.")
  @PostMapping
  fun createOptionGroup(@RequestBody @Valid optionGroup: OptionGroup) = optionGroupUseCase.createOptionGroup(optionGroup)

  @Operation(summary = "옵션그룹 조회", description = "옵션그룹을 조회합니다.")
  @GetMapping("/{id}")
  fun getOptionGroup(@PathVariable id: Long) = optionGroupUseCase.getOptionGroup(id)

  @Operation(summary = "옵션그룹 수정", description = "옵션그룹을 수정합니다.")
  @PutMapping("/{id}")
  fun updateOptionGroup(@PathVariable id: Long, @RequestBody @Valid optionGroup: OptionGroup) = optionGroupUseCase.updateOptionGroup(id, optionGroup)
}