tasks.bootJar {
  enabled = false
}

tasks.jar {
  enabled = true
}

dependencies {
  implementation("org.springframework.cloud:spring-cloud-starter-openfeign")
  implementation("org.springframework.boot:spring-boot-starter-validation")
}