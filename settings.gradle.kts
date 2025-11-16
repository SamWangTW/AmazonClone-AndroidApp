// settings.gradle.kts file is one of the core Gradle configuration files for an Android project.
// Defines how Gradle discovers modules, plugins, and dependencies for your whole app
pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*") // Android Gradle plugin and tools
                includeGroupByRegex("com\\.google.*") // Google libraries (like Play Services)
                includeGroupByRegex("androidx.*") // Jetpack libraries (e.g. androidx.compose.ui)
            }
        }
        mavenCentral() // The central repository for open-source Java/Kotlin libraries (Retrofit, OkHttp, Coil, etc.)
        gradlePluginPortal() // Where Gradle itself publishes community plugins.
    }
}
//This tells Gradle where to get dependencies (libraries) for your app modules (e.g., your app/build.gradle.kts).
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "AmazonCloneApp"
include(":app")
