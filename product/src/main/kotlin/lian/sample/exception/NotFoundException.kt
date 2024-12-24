package lian.sample.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
class NoSuchCategoryException(message: String) : BusinessException(HttpStatus.NOT_FOUND, "카테고리를 찾을 수 업습니다. (${message})")

class NoSuchProductException(message: String) : BusinessException(HttpStatus.NOT_FOUND, "상품을 찾을 수 업습니다. (${message})")

class NoSuchOptionGroupException(message: String) : BusinessException(HttpStatus.NOT_FOUND, "옵션그룹을 찾을 수 업습니다. (${message})")