// Configure settings and dependencies that apply to the entire project.
// Global configuration (repositories, plugin versions, shared tasks)
/* root-level build.gradle.kts plugin block: It declares all the plugins
    your project and modules may need, but doesn’t apply them yet.*/
// plugins { ... }: This block tells Gradle which plugins (tools or frameworks) the project uses — and what versions to use.

plugins {
    id("com.android.application") version "8.11.0" apply false
    id("org.jetbrains.kotlin.android") version "2.1.10" apply false
    id("org.jetbrains.kotlin.plugin.compose") version "2.1.10" apply false
    id("org.jetbrains.kotlin.plugin.serialization") version "2.1.10" apply false
    id("com.google.dagger.hilt.android") version "2.52" apply false
    id("com.google.devtools.ksp") version "2.1.10-1.0.29" apply false
}
// apply false: We declare it here (so submodules can use it), But we don’t apply it to the root project itself.
/*
* The apply false line tells Gradle about the plugin, but doesn’t use it yet.
This way, all your subprojects (modules) can share the same plugin versions without repeating them.
It keeps your setup clean, consistent, and modular.
* */