import feign.*
import feign.codec.ErrorDecoder
import org.springframework.context.annotation.Bean


class ClientConfiguration : ErrorDecoder {
  @Bean
  fun requestInterceptor(): RequestInterceptor {
    return RequestInterceptor { requestTemplate: RequestTemplate ->requestTemplate}
  }

  @Bean
  fun feignLoggerLevel(): Logger.Level {
    return Logger.Level.FULL
  }

  override fun decode(methodKey: String?, response: Response?) =
    FeignException.FeignClientException(
      response!!.status(),
      response.reason() ?: "",
      response.request(),
      ByteArray(1),
      response.headers()
    )
}
