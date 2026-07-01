plugins {
	kotlin("jvm") version "2.2.21"
	kotlin("plugin.spring") version "2.2.21"
	id("org.springframework.boot") version "4.0.1"
	id("io.spring.dependency-management") version "1.1.7"
	kotlin("plugin.jpa") version "2.2.21"
}

group = "api.masterplan"
version = "0.0.1-SNAPSHOT"
description = "MasterPlanApi"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-webmvc")
	implementation("org.springframework.boot:spring-boot-starter-websocket")
    implementation("org.springframework.security:spring-security-crypto")
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:3.0.1")
	implementation("org.springframework.modulith:spring-modulith-bom:2.0.2")
	implementation("org.springframework.modulith:spring-modulith-core:2.0.2")
	implementation("org.springframework.modulith:spring-modulith-api:2.0.2")
	implementation("org.springframework.boot:spring-boot-starter-aop:3.5.10")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("tools.jackson.module:jackson-module-kotlin")
    implementation("io.jsonwebtoken:jjwt:0.13.0")
	runtimeOnly("org.postgresql:postgresql:42.7.11")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("io.mockk:mockk:1.13.8")
	testImplementation("io.rest-assured:rest-assured:6.0.0")
	testImplementation("org.hamcrest:hamcrest:2.2")
	testImplementation("com.fasterxml.jackson.core:jackson-databind:2.16.0")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5:1.9.0")


	implementation("com.github.f4b6a3:uuid-creator:6.1.0")
	implementation("org.bouncycastle:bcprov-jdk18on:1.83")
	implementation("org.apache.poi:poi-ooxml:5.5.1")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.2")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:1.10.2")
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict", "-Xannotation-default-target=param-property")
	}
}

allOpen {
	annotation("jakarta.persistence.Entity")
	annotation("jakarta.persistence.MappedSuperclass")
	annotation("jakarta.persistence.Embeddable")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
