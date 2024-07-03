// Top-level build file where you can add configuration options common to all sub-projects/modules.

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    alias(libs.plugins.kotlin.kapt) apply false
    alias(libs.plugins.dagger.hilt.android) apply false
    alias(libs.plugins.kotlin.parcelize) apply false
    alias(libs.plugins.secrets.gradle.plugin) apply false
    alias(libs.plugins.compose.compiler) apply false
}
allprojects {
    afterEvaluate {
        tasks.withType<JavaCompile>().configureEach {
            // 警告メッセージの設定
            options.compilerArgs.addAll(listOf("-Xlint:unchecked", "-Xlint:deprecation"))
        }
    }
}
