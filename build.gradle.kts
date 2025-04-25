plugins {
    id("java")
    id("idea")

    //grpc plugins
    id("com.google.protobuf") version "0.9.4"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

//grpc stuff
protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:3.25.5"
    }
    plugins {
        create("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:1.72.0"
        }
    }
    generateProtoTasks {
        all().forEach {
            it.plugins {
                create("grpc")
            }
        }
    }
}

//protobuf generated directory
sourceSets["main"].java.srcDir(layout.buildDirectory.dir("generated/source").get().asFile)

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    //grpc dependencies
    runtimeOnly("io.grpc:grpc-netty-shaded:1.72.0")
    implementation("io.grpc:grpc-protobuf:1.72.0")
    implementation("io.grpc:grpc-stub:1.72.0")
    compileOnly("org.apache.tomcat:annotations-api:6.0.53") // necessary for Java 9+
}

tasks.test {
    useJUnitPlatform()
}