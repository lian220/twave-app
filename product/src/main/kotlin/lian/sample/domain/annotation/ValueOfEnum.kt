package kr.co.mustit.validation.annotation

import jakarta.validation.Constraint
import jakarta.validation.Payload
import kotlin.reflect.KClass


@Target(AnnotationTarget.FIELD, AnnotationTarget.TYPE, AnnotationTarget.ANNOTATION_CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [ValueOfEnumValidator::class])
annotation class ValueOfEnum(
  val message: String = "{ValueOfEnum}",
  val groups: Array<KClass<*>> = [],
  val payload: Array<KClass<out Payload>> = [],
  val enumClass: KClass<out Enum<*>>,
)