description = "product module"

tasks.bootJar {enabled = true}
dependencies {
  implementation(project(":infra:security"))

  implementation("jakarta.servlet:jakarta.servlet-api:6.0.0")
  implementation("org.hibernate.validator:hibernate-validator:7.0.1.Final")

  implementation("org.springframework.boot:spring-boot-starter-web")
  implementation("org.springframework.boot:spring-boot-starter-data-jpa:3.0.5")
  implementation("org.springframework.boot:spring-boot-starter-security")

  runtimeOnly("com.mysql:mysql-connector-j")

  api("com.querydsl:querydsl-jpa:5.0.0:jakarta")
  kapt("com.querydsl:querydsl-apt:5.0.0:jakarta")
  annotationProcessor(group = "com.querydsl", name = "querydsl-apt", classifier = "jakarta")

  implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.1.0")
}