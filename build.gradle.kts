plugins {
    id("java")
}

group = "com.example"
version = "1.0-SNAPSHOT"

subprojects {
    apply(plugin = "java")

    repositories {
        mavenCentral()
    }

    dependencies {
        testImplementation(platform("org.junit:junit-bom:5.10.0"))
        testImplementation("org.junit.jupiter:junit-jupiter")
    }

    tasks.named<Test>("test") {
        useJUnitPlatform()
    }
}

tasks.register("checkFile") {
    val filePath = "app2/src/main/resources/text"

    doLast {
        val file = file(filePath)
        if (file.exists()) {
            println("File '$filePath' exists.")
        } else {
            println("File '$filePath' does not exist.")
            throw GradleException("File '$filePath' is missing.")
        }
    }
}

tasks.register("copyFile") {
    val sourcePath = "app2/src/main/resources/text"
    val destinationPath = "app2/build/resources/text"

    doLast {
        val sourceFile = file(sourcePath)
        val destinationFile = file(destinationPath)
        if (!destinationFile.parentFile.exists()) {
            destinationFile.parentFile.mkdirs()
        }
        sourceFile.copyTo(destinationFile, overwrite = true)
        println("File copied to '$destinationPath'.")
    }
}

tasks.register("cleanCopyDir") {
    val directoryPath = "app2/build/resources"

    doLast {
        val directory = file(directoryPath)
        if (directory.exists()) {
            directory.deleteRecursively()
            println("Directory '$directoryPath' cleaned.")
        }
    }
}

tasks.named("build") {
    dependsOn("checkFile")
    dependsOn("cleanCopyDir")
    dependsOn("copyFile")
}
