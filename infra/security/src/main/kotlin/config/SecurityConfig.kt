package config

import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.context.SecurityContextHolderFilter


@Configuration
@EnableWebSecurity
@EnableAutoConfiguration(
  exclude = [UserDetailsServiceAutoConfiguration::class]
)
class SecurityConfiguration {

  @Bean
  fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
    http.csrf { it.disable() }
      .formLogin { it.disable() }  // formLogin을 비활성화
      .authorizeHttpRequests { it
        .requestMatchers("/login").permitAll() // POST 요청만 허용
        .anyRequest().authenticated()
      }
      .headers { it.frameOptions { frameOptions -> frameOptions.sameOrigin() } }
      .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
      .addFilterBefore(JwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter::class.java)

    return http.build()
  }
}


