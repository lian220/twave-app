package lian.sample.exception

import lian.sample.exception.dto.ErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

  @ExceptionHandler(value = [BusinessException::class])
  fun handleBusinessException(e: BusinessException): ResponseEntity<ErrorResponse> =
    ResponseEntity.status(e.httpStatus).body(e.message?.let { ErrorResponse(it) })

  @ExceptionHandler(value = [ValidationIllegalArgumentException::class])
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  fun handleValidationIllegalArgumentException(e: ValidationIllegalArgumentException) =
    e.errors.fieldError?.let { ErrorResponse(it.defaultMessage) }

//  @ExceptionHandler(value = [FeignException::class])
//  fun handleFeignException(e: FeignException): ResponseEntity<ErrorResponse> =
//    ResponseEntity.status(e.status()).body(e.message?.let { ErrorResponse(it) })

  @ExceptionHandler(value = [IllegalArgumentException::class])
  fun handleIllegalArgumentException(e: IllegalArgumentException): ResponseEntity<ErrorResponse> =
    ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.message?.let { ErrorResponse(it) })
}