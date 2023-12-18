import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.2.0"
    id("io.spring.dependency-management") version "1.1.4"
    kotlin("jvm") version "1.9.20"
    kotlin("plugin.spring") version "1.9.20"
    kotlin("plugin.jpa") version "1.9.20"
    kotlin("kapt") version "1.7.10"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}
repositories {
    mavenCentral()
}

dependencies {
    apply(plugin = "kotlin-kapt")
    // DEFAULT
    implementation("org.springframework.boot:spring-boot-starter-web:3.1.0")
    implementation("org.springframework.boot:spring-boot-starter-actuator:3.1.0")
    // LOGGING
    implementation("org.springframework.boot:spring-boot-starter-log4j2:3.0.4")

    // CACHING
    implementation("org.springframework.boot:spring-boot-starter-data-redis")

    // DATABASE
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:3.0.4")
    runtimeOnly("com.mysql:mysql-connector-j:8.0.32")

    // SECURITY
    implementation("org.springframework.boot:spring-boot-starter-security:3.0.4")
    implementation("org.springframework.security:spring-security-jwt:1.1.1.RELEASE")
    implementation ("com.sun.xml.bind:jaxb-impl:4.0.1")
    implementation ("com.sun.xml.bind:jaxb-core:4.0.1")
    implementation ("javax.xml.bind:jaxb-api:2.4.0-b180830.0359")
    implementation("io.jsonwebtoken:jjwt:0.9.1")
    testImplementation("org.springframework.security:spring-security-test:6.0.2")

    // VALIDATING
    implementation("org.springframework.boot:spring-boot-starter-validation:3.0.4")

    // JSON PARSING
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-hibernate5:2.13.3")
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.8.10")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation ("org.glassfish.jaxb:jaxb-runtime:2.3.2")

    // TEST
    testImplementation("org.springframework.boot:spring-boot-starter-test:3.1.0")

    // MAPPING
    implementation("org.mapstruct:mapstruct:1.5.3.Final")
    kapt("org.mapstruct:mapstruct-processor:1.5.3.Final")
    kaptTest("org.mapstruct:mapstruct-processor:1.5.3.Final")

}

configurations.forEach {
    it.exclude(group = "org.springframework.boot", module = "spring-boot-starter-logging")
    it.exclude(group = "org.apache.logging.log4j", module = "log4j-to-slf4j")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
        jvmTarget = "17"
    }
}

allOpen {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.Embeddable")
    annotation("jakarta.persistence.MappedSuperclass")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

