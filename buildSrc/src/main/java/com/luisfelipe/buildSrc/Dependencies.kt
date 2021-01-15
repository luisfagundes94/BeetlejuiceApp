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
    }

    object UI {
        const val constraint_layout = "androidx.constraintlayout:constraintlayout:${Versions.constraint_layout}"
        const val material = "com.google.android.material:material:${Versions.material}"
    }

    object DI {
        const val koin = "org.koin:koin-android:${Versions.koin}"
        const val koin_core = "org.koin:koin-core:${Versions.koin}"
        const val koin_view_model = "org.koin:koin-androidx-viewmodel:${Versions.koin}"
    }

    object Gradle {

    }

    object Test {

    }
}