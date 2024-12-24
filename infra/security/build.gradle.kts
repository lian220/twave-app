tasks.bootJar {
  enabled = false
}

tasks.jar {
  enabled = true
}

dependencies {
  implementation("org.springframework.boot:spring-boot-starter-web")
  implementation("org.springframework.boot:spring-boot-starter-security")
  implementation("com.auth0:java-jwt:4.4.0")

  implementation("io.jsonwebtoken:jjwt-api:0.11.2")
  runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.2")
  runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.2")
}
