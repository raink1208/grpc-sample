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
    implementation("io.netty:netty-all:4.1.77.Final")
    runtimeOnly("io.grpc:grpc-netty:${rootProject.ext["grpcVersion"]}")
}

application {
    mainClass.set("com.github.raink1208.grpcsample.client.HelloWorldClientKt")
}

tasks.jar {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    manifest {
        attributes("Main-Class" to "com.github.raink1208.grpcsample.client.HelloWorldClientKt")
    }
    from(configurations.runtimeClasspath.get()
        .filter { !it.name.endsWith("pom") }
        .onEach { println("add from dependencies:" + it.name) }
        .map { if (it.isDirectory) it else zipTree(it) }
    )
    val sourcesMain = sourceSets.main.get()
    sourcesMain.allSource.forEach { println("add form sources: "+it.name) }
    from(sourcesMain.output)
}