package lian.sample.config

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfig {

  @Bean
  fun openAPI(): OpenAPI {
    return OpenAPI()
      .components(Components())
      .info(configurationInfo())
  }

  private fun configurationInfo(): Info {
    return Info()
      .title("KCD POS 상품 API")
      .description("OpenAPI - Springdoc을 사용한 Swagger UI")
      .version("1.0.0")
  }
}
