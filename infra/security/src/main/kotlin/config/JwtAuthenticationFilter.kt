package config

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthenticationFilter : OncePerRequestFilter() {
  override fun doFilterInternal(
    request: HttpServletRequest,
    response: HttpServletResponse,
    filterChain: FilterChain
  ) {
    val authorizationHeader = request.getHeader("Authorization")
    if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
      val token = authorizationHeader.removePrefix("Bearer ")
      val username = JwtUtil.validateToken(token)
      if (username != null) {
        val authentication = UsernamePasswordAuthenticationToken(username, null, emptyList())
        authentication.details = WebAuthenticationDetailsSource().buildDetails(request)
        SecurityContextHolder.getContext().authentication = authentication
      }
    }
    filterChain.doFilter(request, response)
  }
}