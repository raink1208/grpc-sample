plugins {
    id("com.google.protobuf") version "0.8.18" apply false
    kotlin("jvm") version "1.6.10" apply false
}

ext["grpcVersion"] = "1.43.2"
ext["grpcKotlinVersion"] = "1.2.0"
ext["protobufVersion"] = "3.19.3"
ext["coroutinesVersion"] = "1.6.0-native-mt"

allprojects {
    repositories {
        mavenLocal()
        mavenCentral()
        google()
    }
}