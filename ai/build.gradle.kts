import com.varabyte.kobweb.gradle.library.util.configAsKobwebLibrary

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kobweb.library)
    alias(libs.plugins.jetbrains.dokka)
    `maven-publish`
}

group = "com.github.dead8309"
version = libs.versions.chrome.ai.get()

kotlin {
    configAsKobwebLibrary(includeServer = false)

    sourceSets {
        val jsMain by getting {
            dependencies {
                api(libs.kotlin.web)
            }
        }
    }
}

tasks{
    register<Jar>("dokkaJar") {
        from(dokkaHtml)
        dependsOn(dokkaHtml)
        archiveClassifier.set("javadoc")
    }
    withType<Jar> {
        metaInf.with(
            copySpec {
                from("${project.rootDir}/LICENSE")
            }
        )
    }
}

publishing {
    publications {
        register("mavenJsLibrary", MavenPublication::class) {
            from(components["kotlin"])
            groupId = "com.github.dead8309"
            artifactId = "chrome-ai"
            version = version
        }
    }
}