plugins {
    kotlin("jvm")
    application
}

group = "com.github.raink1208"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":common"))
    runtimeOnly("io.grpc:grpc-netty:${rootProject.ext["grpcVersion"]}")
}

application {
    mainClass.set("com.github.raink1208.grpcsample.client.HelloWorldClientKt")
}