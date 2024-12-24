package lian.sample.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "옵션을 찾을 수 없습니다.")
class OptionValidationException() : BusinessException(HttpStatus.BAD_REQUEST.toString())