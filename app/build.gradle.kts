plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    // APIキーを隠すためのプラグイン
    alias(libs.plugins.secrets.gradle.plugin)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.dagger.hilt.android)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.detekt)
    alias(libs.plugins.serialization)
    alias(libs.plugins.compose.compiler)
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
        vectorDrawables {
            useSupportLibrary = true
        }
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
        debug {
            signingConfig = signingConfigs.getByName("debug")
            isDebuggable = true
            versionNameSuffix = ".D"
            applicationIdSuffix = ".debug"
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
        freeCompilerArgs += "-opt-in=androidx.compose.material3.ExperimentalMaterial3Api"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    hilt {
        enableAggregatingTask = true
    }
    testOptions {
        // テスト時のアニメーションを無効化
        animationsDisabled = true
    }
    lint {
        sarifReport = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}
composeCompiler {
    enableStrongSkippingMode = true

    reportsDestination = layout.buildDirectory.dir("compose_compiler")
}
dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.navigation.runtime.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.accompanist.permissions)

    implementation(libs.kotlinx.collections.immutable)

    // 位置情報取得ライブラリ
    implementation(libs.play.services.location)
    // retrofit
    implementation(libs.retrofit)
    // MoshiのConverterを使うためのJsonパースライブラリ
    implementation(libs.retrofit.converter.moshi)
    // Jsonパーサライブラリ
    implementation(libs.moshi.kotlin)
    // 画像表示ライブラリ
    implementation(libs.coil)

    // Dagger-Hilt
    implementation(libs.dagger.hilt.android)
    implementation(libs.androidx.lifecycle.runtime.compose.android)
    kapt(libs.dagger.hilt.android.compiler)

    // メモリリーク検出ライブラリ
    debugImplementation(libs.leakcanary)

    // detektフォーマット
    detektPlugins(libs.detekt.formatting)
    detektPlugins(libs.detekt.rules)
    detektPlugins(libs.detekt.rules.twitter)

    // Kotlin Serialization
    implementation(libs.kotlinx.serialization.json)

    // DataStore
    implementation(libs.androidx.datastore.preferences)

    implementation(libs.androidx.compose.material.iconsExtended)

    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

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
    androidTestImplementation(libs.androidx.ui.test.junit4.android)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.uiautomator)
}

kapt {
    // エラータイプの修正を有効化
    correctErrorTypes = true
}

detekt {
    parallel = true
    config.setFrom("${rootProject.projectDir}/config/detekt/detekt.yml")
    buildUponDefaultConfig = true
    autoCorrect = true // 自動でフォーマット
    basePath = rootDir.absolutePath
}
