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

    }

    object UI {
        const val constraint_layout = "androidx.constraintlayout:constraintlayout:${Versions.constraint_layout}"
        const val material = "com.google.android.material:material:${Versions.material}"
    }

    object DI {

    }

    object Gradle {

    }

    object Test {

    }
}