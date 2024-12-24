package lian.sample.presentation

import config.JwtUtil
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class LoginController {
  @PostMapping("/login")
  fun login(@RequestBody request: LoginRequest): String {
    val token = JwtUtil.generateToken(username = "testUser")
    return token
  }
}

data class LoginRequest(val username: String, val password: String)