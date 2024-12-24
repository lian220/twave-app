package kr.co.mustit.validation.annotation

import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext


class ValueOfEnumValidator : ConstraintValidator<ValueOfEnum, CharSequence?> {
  private var acceptedValues: List<String>? = null

  override fun initialize(annotation: ValueOfEnum) {
    acceptedValues = annotation.enumClass.java.enumConstants
      .map { obj: Enum<*> -> obj.name }
  }

  override fun isValid(value: CharSequence?, context: ConstraintValidatorContext): Boolean {
    if (value == null) {
      return true
    }

    return acceptedValues!!.contains(value.toString())
  }
}
