package lian.sample.exception

import org.springframework.validation.Errors

class ValidationIllegalArgumentException(errors: Errors) : IllegalArgumentException() {
  var errors: Errors = errors
    private set
}