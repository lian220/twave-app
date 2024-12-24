package config

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import java.util.*

class JwtUtil {

  companion object {
    private const val SECRET = "twave-app"
    private const val EXPIRATION_TIME = 3600000L

    fun generateToken(username: String): String {
      return JWT.create()
        .withSubject(username)
        .withExpiresAt(Date(System.currentTimeMillis() + EXPIRATION_TIME))
        .sign(Algorithm.HMAC256(SECRET))
    }

    fun validateToken(token: String): String? {
      return try {
        val verifier = JWT.require(Algorithm.HMAC256(SECRET)).build()
        val decodedJWT = verifier.verify(token)
        decodedJWT.subject
      } catch (e: Exception) {
        null
      }
    }
  }

}