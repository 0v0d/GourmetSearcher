@file:Suppress("UnstableApiUsage")

val ktlint: Configuration by configurations.creating
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.androidx.navigation.safeargs)
    // APIキーを隠すためのプラグイン
    alias(libs.plugins.secrets.gradle.plugin)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.dagger.hilt.android)
    alias(libs.plugins.kotlin.kapt)
}

android {
    namespace = "com.example.gourmetsearcher"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.gourmetsearcher"
        minSdk = 32
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isShrinkResources = true
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        buildConfig = true
        dataBinding = true
        viewBinding = true
    }
    hilt {
        enableAggregatingTask = true
    }
    testOptions {
        // テスト時のアニメーションを無効化
        animationsDisabled = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.runtime.ktx)
    // NavHostFragmentのために
    implementation(libs.androidx.navigation.fragment.ktx)
    // Toolbarのために
    implementation(libs.androidx.navigation.ui.ktx)
    // 位置情報取得ライブラリ
    implementation(libs.play.services.location)
    // retrofit
    implementation(libs.retrofit)
    // MoshiのConverterを使うためのJsonパースライブラリ
    implementation(libs.retrofit.converter.moshi)
    // Jsonパーサライブラリ
    implementation(libs.moshi.kotlin)
    // 画像表示ライブラリ
    implementation(libs.picasso)

    // Dagger-Hilt
    implementation(libs.dagger.hilt.android)
    kapt(libs.dagger.hilt.android.compiler)

    // メモリリーク検出ライブラリ
    debugImplementation(libs.leakcanary)

    // MockitoJUnitRunner
    testImplementation(libs.mockito.core)
    testImplementation(libs.junit)
    testImplementation(libs.dagger.hilt.android.testing)
    kaptTest(libs.dagger.hilt.android.compiler)
    testImplementation(libs.androidx.runner)
    testImplementation(libs.androidx.core.testing)
    testImplementation(libs.kotlinx.coroutines.test)

    // Espresso
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.rules)
    androidTestImplementation(libs.androidx.espresso.contrib)
    androidTestImplementation(libs.androidx.uiautomator.v18)

    ktlint(libs.ktlint) {
        attributes {
            attribute(Bundling.BUNDLING_ATTRIBUTE, objects.named(Bundling.EXTERNAL))
        }
    }
}
kapt {
    // エラータイプの修正を有効化
    correctErrorTypes = true
}

val outputDir = "${project.layout.buildDirectory.get().asFile}/reports/ktlint/"
val inputFiles = project.fileTree(mapOf("dir" to "src", "include" to "**/*.kt"))

val ktlintCheck by tasks.creating(JavaExec::class) {
    inputs.files(inputFiles)
    outputs.dir(outputDir)

    group = "ktlint"
    description = "Check Kotlin code style."
    classpath = ktlint
    mainClass.set("com.pinterest.ktlint.Main")
    args = listOf("src/**/*.kt")
}

val ktlintFormat by tasks.creating(JavaExec::class) {
    inputs.files(inputFiles)
    outputs.dir(outputDir)

    group = "ktlint"
    description = "Fix Kotlin code style deviations."
    classpath = ktlint
    mainClass.set("com.pinterest.ktlint.Main")
    args = listOf("-F", "src/**/*.kt")
}
