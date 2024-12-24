plugins {
	java
	id("org.springframework.boot") version "3.0.6"
	id("io.spring.dependency-management") version "1.1.0"
	val kotlinVersion = "1.7.22"

	kotlin("jvm") version kotlinVersion
	kotlin("plugin.spring") version kotlinVersion
	kotlin("plugin.jpa") version kotlinVersion
	kotlin("kapt") version kotlinVersion
}

extra["springCloudVersion"] = "2022.0.2"

java {
	sourceCompatibility = JavaVersion.VERSION_17
	targetCompatibility = JavaVersion.VERSION_17
}

allprojects {
	group = "lian.sample"
	version = "0.0.1-SNAPSHOT"

	repositories {
		mavenCentral()
	}

	tasks.withType<Test> {
		useJUnitPlatform()
	}
}

allOpen {
	annotation("javax.persistence.Entity")
	annotation("javax.persistence.Embeddable")
	annotation("javax.persistence.MappedSuperclass")
}

subprojects {
	apply(plugin = "org.springframework.boot")
	apply(plugin = "io.spring.dependency-management")

	apply(plugin = "org.jetbrains.kotlin.jvm")
	apply(plugin = "org.jetbrains.kotlin.plugin.spring")
	apply(plugin = "org.jetbrains.kotlin.plugin.jpa")
	apply(plugin = "org.jetbrains.kotlin.kapt")

	configurations {
		compileOnly {
			extendsFrom(configurations.annotationProcessor.get())
		}
	}

	repositories {
		mavenCentral()
	}

	dependencies {
		implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
		implementation("org.springframework.boot:spring-boot-starter")
		implementation("org.springframework.boot:spring-boot-starter-data-jpa")
		implementation("com.mysql:mysql-connector-j")

		implementation("org.jetbrains.kotlin:kotlin-reflect")
		implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

		kapt("org.springframework.boot:spring-boot-configuration-processor")

		// log4j2 & MDC
		implementation("org.springframework.boot:spring-boot-starter-log4j2")
		implementation("org.slf4j:slf4j-api:2.0.7")

		//log & lombok
		compileOnly("org.projectlombok:lombok")
		annotationProcessor("org.projectlombok:lombok")

		developmentOnly("org.springframework.boot:spring-boot-devtools")

		//test 용
		testImplementation("org.springframework.boot:spring-boot-starter-log4j2")
		testImplementation("org.springframework.boot:spring-boot-starter-test")
	}

	dependencyManagement {
		imports {
			mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
		}
	}

	tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
		kotlinOptions {
			freeCompilerArgs = listOf("-Xjsr305=strict")
			jvmTarget = "17"
		}
	}

	configurations {
		all {
			//log4j2 충돌 방지
			exclude("org.springframework.boot", "spring-boot-starter-logging")
		}
	}

	noArg {
		annotation("jakarta.persistence.Entity")
	}

	tasks.withType<Test> {
		useJUnitPlatform()
	}
}

project(":infra") {
	tasks.bootJar {enabled = false}
	tasks.jar {enabled = true}
}

project(":order") {
	tasks.bootJar {enabled = true}
	dependencies {
		implementation("jakarta.servlet:jakarta.servlet-api:6.0.0")
	}
}

project(":product") {}


