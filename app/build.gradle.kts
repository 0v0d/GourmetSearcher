plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.androidx.navigation.safeargs)
    // APIキーを隠すためのプラグイン
    alias(libs.plugins.secrets.gradle.plugin)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.dagger.hilt.android)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.detekt)
    alias(libs.plugins.serialization)
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
    lint {
        sarifReport = true
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
    implementation(libs.coil)

    // Dagger-Hilt
    implementation(libs.dagger.hilt.android)
    kapt(libs.dagger.hilt.android.compiler)

    // メモリリーク検出ライブラリ
    debugImplementation(libs.leakcanary)

    // detektフォーマット
    detektPlugins(libs.detekt.formatting)

    // Kotlin Serialization
    implementation(libs.kotlinx.serialization.json)

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
}
kapt {
    // エラータイプの修正を有効化
    correctErrorTypes = true
}
detekt {
    config.setFrom("${rootProject.projectDir}/config/detekt/detekt.yml")
    buildUponDefaultConfig = true
    autoCorrect = true // 自動でフォーマット
}
