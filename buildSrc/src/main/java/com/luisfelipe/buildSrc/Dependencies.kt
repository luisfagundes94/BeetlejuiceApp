package com.luisfelipe.buildSrc

object Dependencies {

    object Core {
        const val kotlin_stdlib = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}"
        const val core_ktx = "androidx.core:core-ktx:${Versions.core_ktx}"
        const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
        const val navigation_fragment= "androidx.navigation:navigation-fragment-ktx:${Versions.navigation}"
        const val navigation_ui = "androidx.navigation:navigation-ui-ktx:${Versions.navigation}"
    }

    object Data {
        const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
        const val gson = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
        const val ok_http = "com.squareup.okhttp3:logging-interceptor:${Versions.ok_http}"
        const val preferences_ktx = "androidx.preference:preference-ktx:${Versions.preferences_ktx}"
    }

    object UI {
        const val constraint_layout = "androidx.constraintlayout:constraintlayout:${Versions.constraint_layout}"
        const val material = "com.google.android.material:material:${Versions.material}"
        const val picasso = "com.squareup.picasso:picasso:${Versions.picasso}"
    }

    object DI {
        const val koin = "org.koin:koin-android:${Versions.koin}"
        const val koin_core = "org.koin:koin-core:${Versions.koin}"
        const val koin_view_model = "org.koin:koin-androidx-viewmodel:${Versions.koin}"
    }

    object Gradle {

    }

    object Test {
        const val junit = "junit:junit:${Versions.junit}"
        const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutines}"
        const val mockk = "io.mockk:mockk:${Versions.mockk}"
        const val arch_core = "androidx.arch.core:core-testing:${Versions.arch_core}"
    }

    object Lifecycle {
        const val liveData = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.liveData}"
    }
}